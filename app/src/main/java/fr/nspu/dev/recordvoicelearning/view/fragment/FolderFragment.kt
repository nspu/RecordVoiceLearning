package fr.nspu.dev.recordvoicelearning.view.fragment


import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.support.v4.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.ToggleButton

import fr.nspu.dev.recordvoicelearning.R
import fr.nspu.dev.recordvoicelearning.RecordVoiceLearning
import fr.nspu.dev.recordvoicelearning.controller.entity.FolderEntity
import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity
import fr.nspu.dev.recordvoicelearning.databinding.FragmentFolderBinding
import fr.nspu.dev.recordvoicelearning.utils.OrderPeerEnum
import fr.nspu.dev.recordvoicelearning.utils.QuestionToAnswerEnum
import fr.nspu.dev.recordvoicelearning.utils.callback.ClickCallback
import fr.nspu.dev.recordvoicelearning.view.activity.ListenPeersActivity
import fr.nspu.dev.recordvoicelearning.view.activity.RecordActivity
import fr.nspu.dev.recordvoicelearning.view.adapter.PeerAdapter
import fr.nspu.dev.recordvoicelearning.viewmodel.FolderViewModel
import java.util.*

class FolderFragment : Fragment() {

    private var mBinding: FragmentFolderBinding? = null
    private var mPeerAdapter: PeerAdapter? = null

    private val mPeerClickCallback = object : ClickCallback {
        override fun onClick(o: Any) {
            val peer = o as PeerEntity
            Toast.makeText(context, Integer.toString(peer.knowledge!!), Toast.LENGTH_SHORT).show()
        }

        override fun onLongClick(o: Any): Boolean {
            val peer = o as PeerEntity
            return false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_folder, container, false)
        mPeerAdapter = PeerAdapter(mPeerClickCallback, context!!)
        mBinding!!.peersList.adapter = mPeerAdapter

        mBinding!!.btnPlayPeers.setOnClickListener { v:View? ->
            val intent = Intent(
                    activity,
                    ListenPeersActivity::class.java
            )
            intent.putExtra(KEY_FOLDER_ID, arguments!!.getInt(KEY_FOLDER_ID))
            val folderEntity = mBinding!!.folderViewModel!!.folder.get()
            if (folderEntity != null) {
                intent.putExtra(KEY_ORDER, folderEntity.order!!.toInt())
                intent.putExtra(KEY_QUESTION_TO_ANSWER, folderEntity.questionToAnswer!!.toBoolean())
            } else {
                intent.putExtra(KEY_ORDER, OrderPeerEnum.KNOWLEDGE_ASCENDING.toInt())
                intent.putExtra(KEY_QUESTION_TO_ANSWER, QuestionToAnswerEnum.QUESTION_TO_ANSWER.toBoolean())
            }

            startActivity(intent)
        }

        mBinding!!.btnOrder.setOnClickListener { v:View? ->
            val folderEntity = mBinding!!.folderViewModel!!.folder.get()
            val isChecked = (v as ToggleButton).isChecked
            if (folderEntity != null && isChecked) {
                folderEntity.order = OrderPeerEnum.KNOWLEDGE_ASCENDING
                UpdateFolderAsync().execute(folderEntity)
            } else {
                folderEntity!!.order = OrderPeerEnum.KNOWLEDGE_DESCENDING
                UpdateFolderAsync().execute(folderEntity)
            }
        }


        mBinding!!.btnQuestionToAnswer.setOnCheckedChangeListener { buttonView:View?, isChecked:Boolean ->
            val folderEntity = mBinding!!.folderViewModel!!.folder.get()
            if (folderEntity != null && isChecked) {
                folderEntity.questionToAnswer = QuestionToAnswerEnum.QUESTION_TO_ANSWER
                UpdateFolderAsync().execute(folderEntity)
            } else {
                folderEntity!!.questionToAnswer = QuestionToAnswerEnum.ANSWER_TO_QUESTION
                UpdateFolderAsync().execute(folderEntity)
            }
        }



        return mBinding!!.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.folder_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item!!.itemId

        when (id) {
            R.id.action_add_peer -> {
                val intent = Intent(activity!!.applicationContext, RecordActivity::class.java)
                intent.putExtra(KEY_FOLDER_ID, arguments!!.getInt(KEY_FOLDER_ID))
                startActivity(intent)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = FolderViewModel.Factory(
                activity!!.application, arguments!!.getInt(KEY_FOLDER_ID))

        val model = ViewModelProviders.of(this, factory)
                .get(FolderViewModel::class.java)

        mBinding!!.folderViewModel = model

        subscribeToModel(model)
    }

    private fun subscribeToModel(model: FolderViewModel) {

        // Observe folder data
        model.observableFolder.observe(this, Observer { model.setFolder(it) })

        model.observableFolder.observe(this, Observer { folderEntity ->
            // Observe peers
            model.peers.observe(this@FolderFragment, Observer { peerEntities ->
                if (peerEntities != null) {
                    mBinding!!.isLoading = false
                    if (folderEntity!!.order == OrderPeerEnum.KNOWLEDGE_ASCENDING) {
                        Collections.sort(peerEntities) { o1, o2 -> Integer.compare(o1.knowledge!!, o2.knowledge!!) }
                    } else {
                        Collections.sort(peerEntities) { o1, o2 -> Integer.compare(o2.knowledge!!, o1.knowledge!!) }
                    }
                    mPeerAdapter!!.setPeerList(peerEntities)
                    //notify to display the correct order
                    mPeerAdapter!!.notifyDataSetChanged()
                    mBinding!!.count = peerEntities.size
                    if (peerEntities.size > 0) {
                        mBinding!!.btnPlayPeers.isEnabled = true
                    } else {
                        mBinding!!.btnPlayPeers.isEnabled = false
                    }
                } else {
                    mBinding!!.isLoading = true
                }
            })
        })
    }

    @SuppressLint("StaticFieldLeak")
    private inner class UpdateFolderAsync : AsyncTask<FolderEntity, Void, Unit>() {
        override fun doInBackground(vararg folderEntities: FolderEntity) {
            var folderEntity = folderEntities[0]
            (activity!!.application as RecordVoiceLearning)
                    .database
                    .folderDao()
                    .updateFoldersSync(folderEntity)
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)

        }
    }

    companion object {
        val KEY_FOLDER_ID = "id_folder"
        val KEY_ORDER = "ordre_peer"
        val KEY_QUESTION_TO_ANSWER = "question_to_anwser"

        fun forFolder(idFolder: Int, questionToAnswer: QuestionToAnswerEnum): FolderFragment {
            val fragment = FolderFragment()
            val args = Bundle()
            args.putInt(KEY_FOLDER_ID, idFolder)
            args.putBoolean(KEY_QUESTION_TO_ANSWER, questionToAnswer.toBoolean())
            fragment.arguments = args
            return fragment
        }
    }
}

