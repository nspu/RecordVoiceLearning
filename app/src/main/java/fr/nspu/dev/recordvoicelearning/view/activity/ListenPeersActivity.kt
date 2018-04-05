package fr.nspu.dev.recordvoicelearning.view.activity

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import java.util.Collections

import fr.nspu.dev.recordvoicelearning.RecordVoiceLearning
import fr.nspu.dev.recordvoicelearning.R
import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity
import fr.nspu.dev.recordvoicelearning.databinding.FragmentListenPeersBinding
import fr.nspu.dev.recordvoicelearning.utils.OrderPeerEnum
import fr.nspu.dev.recordvoicelearning.utils.ListenVoice
import fr.nspu.dev.recordvoicelearning.utils.QuestionToAnswerEnum
import fr.nspu.dev.recordvoicelearning.view.fragment.FolderFragment

import fr.nspu.dev.recordvoicelearning.view.fragment.FolderFragment.Companion.KEY_FOLDER_ID
import fr.nspu.dev.recordvoicelearning.view.fragment.FolderFragment.Companion.KEY_ORDER

class ListenPeersActivity : AppCompatActivity() {

    private var mPeers: List<PeerEntity>? = null

    private var mOrder: OrderPeerEnum? = null

    private var mQuestionToAnswer: QuestionToAnswerEnum? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listen_peers)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val folderId = intent.getIntExtra(KEY_FOLDER_ID, -1)

        if (folderId == -1) {
            //TODO error
            finish()
        }

        mOrder = OrderPeerEnum.toOrderPeerEnum(intent.getIntExtra(KEY_ORDER, 0))
        mQuestionToAnswer = QuestionToAnswerEnum.toQuestionToAnswerEnum(
                intent.getBooleanExtra(FolderFragment.KEY_QUESTION_TO_ANSWER, true))

        this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar!!.setDisplayShowHomeEnabled(true)

        LoadFolderPeersTask().execute(folderId)
    }

    private fun init() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        /*
      The {@link android.support.v4.view.PagerAdapter} that will provide
      fragments for each of the sections. We use a
      {@link FragmentPagerAdapter} derivative, which will keep every
      loaded fragment in memory. If this becomes too memory intensive, it
      may be best to switch to a
      {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
        val mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        if (mOrder == OrderPeerEnum.KNOWLEDGE_ASCENDING) {
            Collections.sort(mPeers!!) { o1, o2 -> Integer.compare(o1.knowledge!!, o2.knowledge!!) }
        } else {
            Collections.sort(mPeers!!) { o1, o2 -> Integer.compare(o2.knowledge!!, o1.knowledge!!) }
        }

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container)
        mViewPager!!.adapter = mSectionsPagerAdapter

        initViewPagerChangeListener()
    }

    /**
     * If autoplay is TRUE, play the question at start  when the fragment is seen.
     *
     * Subscribe to SimpleOnPageChangeListener in ViewPager.
     * When onPageSelected is called, call autoPlayStart with the setting selected.
     */
    private fun initViewPagerChangeListener() {
        val pageChangeListener = object : ViewPager.SimpleOnPageChangeListener() {
            internal var firstCall = false
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                //onPageSelected is not called when the first element is display but onPageScrolled yes
                //So we call one time onPageSeleced with the first position
                if (!firstCall) {
                        this.onPageSelected(0)
                    firstCall = true
                }
            }

            //call when a fragment is displayed(except for the first element)
            override fun onPageSelected(position: Int) {
                val isAutoPlay: Boolean
                val f = mViewPager!!.adapter!!.instantiateItem(mViewPager!!, position) as ListenPeerFragment
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

                isAutoPlay = sharedPreferences.getBoolean(getString(R.string.settings_auto_play), true)
                f.autoPlayAtStart(isAutoPlay)
            }

        }
        mViewPager!!.addOnPageChangeListener(pageChangeListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * A listenpeer fragment containing the view for listen and indicate if the answer is correct.
     */
    class ListenPeerFragment : Fragment() {

        private var mBinding: FragmentListenPeersBinding? = null
        private var mPeer: PeerEntity? = null
        private var mPosition: Int = 0
        private var mSize: Int = 0
        private var mQuestionFile: String? = null
        private var mAnswerFile: String? = null
        private var mListenQuestion: ListenVoice? = null
        private var mListenAnswer: ListenVoice? = null


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            userVisibleHint = false


            val questionToAnswer = QuestionToAnswerEnum.toQuestionToAnswerEnum(
                    arguments!!.getBoolean(KEY_QUESTION_TO_ANSWER))
            mPeer = arguments!!.getSerializable(KEY_PEER_OBJECT) as PeerEntity
            mPosition = arguments!!.getInt(KEY_POSITION)
            mSize = arguments!!.getInt(KEY_SIZE_PEERS)
            if (questionToAnswer == QuestionToAnswerEnum.QUESTION_TO_ANSWER) {
                mQuestionFile = mPeer!!.fileNameQuestion
                mAnswerFile = mPeer!!.fileNameAnswer
            } else {
                mQuestionFile = mPeer!!.fileNameAnswer
                mAnswerFile = mPeer!!.fileNameQuestion
            }
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_listen_peers, container, false)

            mBinding!!.tvPeerName.text = context!!.getString(R.string.peer_idpeer, mPeer!!.id)
            mBinding!!.tvPositionTotal.text = context!!.getString(R.string._slash_, mPosition + 1, mSize)
            mBinding!!.sbKnowledge.progress = mPeer!!.knowledge!!

            mListenQuestion = listen(mBinding!!.btnListenQuestion, mQuestionFile)
            mListenAnswer = listen(mBinding!!.btnListenAnswer, mAnswerFile)

            mBinding!!.btnGood.setOnClickListener { _:View? ->
                mPeer!!.increaseKnowledge()
                updatePeer()
            }

            mBinding!!.btnWrong.setOnClickListener { _:View? ->
                mPeer!!.descreaseKnowledge()
                updatePeer()
            }


            return mBinding!!.root
        }

        fun autoPlayAtStart(isAutoPlay: Boolean) {
            if (isAutoPlay) {
                mListenQuestion!!.startPlaying()
            }
        }

        private fun listen(button: Button, fileName: String?): ListenVoice {
            val directoryFinal = context!!.getExternalFilesDir(null)!!.absolutePath
            return ListenVoice(button, fileName!!, directoryFinal)
        }

        private fun updatePeer() {
            UpdatePeerTask().execute(mPeer)
        }

        private operator fun next() {
            if (mSize > mPosition + 1) {
                mBinding!!.sbKnowledge.progress = mPeer!!.knowledge!!
                mViewPager!!.currentItem = mPosition + 1
            } else {
                activity!!.finish()
            }
        }

        @SuppressLint("StaticFieldLeak")
        private inner class UpdatePeerTask : AsyncTask<PeerEntity, Void, Unit>() {

            override fun doInBackground(vararg peer: PeerEntity){

                (activity!!.application as RecordVoiceLearning).database!!
                        .peerDao()
                        .updatePeersSync(*peer)
            }

            override fun onPostExecute(result: Unit) {
                super.onPostExecute(result)
                next()
            }
        }

        companion object {
            private val KEY_PEER_OBJECT = "peer_object"
            private val KEY_POSITION = "position"
            private val KEY_SIZE_PEERS = "size_peers"
            private val KEY_QUESTION_TO_ANSWER = "question_to_answer"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(peer: PeerEntity, position: Int, size: Int, questionToAnswer: QuestionToAnswerEnum?): ListenPeerFragment {
                val fragment = ListenPeerFragment()
                fragment.tag
                val args = Bundle()
                args.putSerializable(KEY_PEER_OBJECT, peer)
                args.putInt(KEY_POSITION, position)
                args.putInt(KEY_SIZE_PEERS, size)
                args.putBoolean(KEY_QUESTION_TO_ANSWER, questionToAnswer!!.toBoolean())
                fragment.arguments = args
                return fragment
            }
        }
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a ListenPeerFragment (defined as a static inner class below).
            return ListenPeerFragment.newInstance(mPeers!![position], position, mPeers!!.size, mQuestionToAnswer)
        }

        override fun getCount(): Int {
            return mPeers!!.size
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class LoadFolderPeersTask : AsyncTask<Int, Void, Unit>() {

         override fun doInBackground(vararg integers: Int?){
            mPeers = integers[0]!!.let { (application as RecordVoiceLearning).database!!
                    .peerDao()
                    .loadAllPeerByFolderIdSync(it)}
        }

        override fun onPostExecute(result: Unit) {
            super.onPostExecute(result)
            init()
        }
    }

    companion object {
        /**
         * The [ViewPager] that will host the section contents.
         */
        private var     mViewPager: ViewPager? = null
    }
}
