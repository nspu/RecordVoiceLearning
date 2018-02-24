package fr.nspu.dev.recordvoicelearning.view;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Collections;
import java.util.List;

import fr.nspu.dev.recordvoicelearning.RecordVoiceLearning;
import fr.nspu.dev.recordvoicelearning.R;
import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity;
import fr.nspu.dev.recordvoicelearning.databinding.FragmentListenPeersBinding;
import fr.nspu.dev.recordvoicelearning.utils.OrderPeerEnum;
import fr.nspu.dev.recordvoicelearning.utils.ListenVoice;
import fr.nspu.dev.recordvoicelearning.utils.QuestionToAnswerEnum;
import fr.nspu.dev.recordvoicelearning.view.fragment.FolderFragment;

import static fr.nspu.dev.recordvoicelearning.view.fragment.FolderFragment.KEY_FOLDER_ID;
import static fr.nspu.dev.recordvoicelearning.view.fragment.FolderFragment.KEY_ORDER;

public class ListenPeersActivity extends AppCompatActivity {

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private static ViewPager mViewPager;

    private List<PeerEntity> mPeers;

    private OrderPeerEnum mOrder;

    private QuestionToAnswerEnum mQuestionToAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_peers);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int folderId = getIntent().getIntExtra(KEY_FOLDER_ID, -1);

        if (folderId == -1) {
            //TODO error
            finish();
        }

        mOrder = OrderPeerEnum.toOrderPeerEnum(getIntent().getIntExtra(KEY_ORDER, 0));
        mQuestionToAnswer = QuestionToAnswerEnum.toQuestionToAnswerEnum(
                getIntent().getBooleanExtra(FolderFragment.KEY_QUESTION_TO_ANSWER, true));

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);

        new ListenPeersActivity.LoadFolderPeersTask().execute(folderId);
    }

    private void init() {
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
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        if (mOrder == OrderPeerEnum.KNOWLEDGE_ASCENDING) {
            Collections.sort(mPeers, (o1, o2) -> Integer.compare(o1.getKnowledge(), o2.getKnowledge()));
        } else {
            Collections.sort(mPeers, (o1, o2) -> Integer.compare(o2.getKnowledge(), o1.getKnowledge()));
        }

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        initViewPagerChangeListener();
    }

    /**
     * If autoplay is TRUE, play the question at start  when the fragment is seen.
     *
     * Subscribe to SimpleOnPageChangeListener in ViewPager.
     * When onPageSelected is called, call autoPlayStart with the setting selected.
     */
    private void initViewPagerChangeListener(){
        ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
            boolean firstCall = false;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                //onPageSelected is not called when the first element is display but onPageScrolled yes
                //So we call one time onPageSeleced with the first position
                if(!firstCall){
                    this.onPageSelected(0);
                    firstCall = true;
                }
            }

            //call when a fragment is displayed(except for the first element)
            @Override
            public void onPageSelected(int position) {
                boolean isAutoPlay;
                ListenPeerFragment f = (ListenPeerFragment)mViewPager.getAdapter().instantiateItem(mViewPager, position);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                isAutoPlay = sharedPreferences.getBoolean(getString(R.string.settings_auto_play), true);
                f.autoPlayAtStart(isAutoPlay);
            }

        };
        mViewPager.addOnPageChangeListener(pageChangeListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A listenpeer fragment containing the view for listen and indicate if the answer is correct.
     */
    public static class ListenPeerFragment extends Fragment {
        private static final String KEY_PEER_OBJECT = "peer_object";
        private static final String KEY_POSITION = "position";
        private static final String KEY_SIZE_PEERS = "size_peers";
        private static final String KEY_QUESTION_TO_ANSWER = "question_to_answer    ";

        private FragmentListenPeersBinding mBinding;
        private PeerEntity mPeer;
        private int mPosition;
        private int mSize;
        private String mQuestionFile;
        private String mAnswerFile;
        private ListenVoice mListenQuestion;
        private ListenVoice mListenAnswer;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ListenPeerFragment newInstance(PeerEntity peer, int position, int size, QuestionToAnswerEnum questionToAnswer) {
            ListenPeerFragment fragment = new ListenPeerFragment();
            fragment.getTag();
            Bundle args = new Bundle();
            args.putSerializable(KEY_PEER_OBJECT, peer);
            args.putInt(KEY_POSITION, position);
            args.putInt(KEY_SIZE_PEERS, size);
            args.putBoolean(KEY_QUESTION_TO_ANSWER, questionToAnswer.toBoolean());
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setUserVisibleHint(false);


            QuestionToAnswerEnum questionToAnswer =
                    QuestionToAnswerEnum.toQuestionToAnswerEnum(
                            getArguments().getBoolean(KEY_QUESTION_TO_ANSWER));
            mPeer = (PeerEntity) getArguments().getSerializable(KEY_PEER_OBJECT);
            mPosition = getArguments().getInt(KEY_POSITION);
            mSize = getArguments().getInt(KEY_SIZE_PEERS);
            if (questionToAnswer == QuestionToAnswerEnum.QUESTION_TO_ANSWER) {
                mQuestionFile = mPeer.getFileNameQuestion();
                mAnswerFile = mPeer.getFileNameAnswer();
            } else {
                mQuestionFile = mPeer.getFileNameAnswer();
                mAnswerFile = mPeer.getFileNameQuestion();
            }
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_listen_peers, container, false);

            mBinding.tvPeerName.setText(getContext().getString(R.string.peer_idpeer, mPeer.getId()));
            mBinding.tvPositionTotal.setText(getContext().getString(R.string._slash_, mPosition + 1, mSize));
            mBinding.sbKnowledge.setProgress(mPeer.getKnowledge());

            mListenQuestion = listen(mBinding.btnListenQuestion, mQuestionFile);
            mListenAnswer = listen(mBinding.btnListenAnswer, mAnswerFile);

            mBinding.btnGood.setOnClickListener(v -> {
                mPeer.increaseKnowledge();
                updatePeer();
            });

            mBinding.btnWrong.setOnClickListener(v -> {
                mPeer.descreaseKnowledge();
                updatePeer();
            });


            return mBinding.getRoot();
        }

        public void autoPlayAtStart(boolean isAutoPlay){
            if (isAutoPlay) {
                mListenQuestion.startPlaying();
            }
        }

        private ListenVoice listen(Button button, String fileName) {
            String directoryFinal = getContext().getExternalFilesDir(null).getAbsolutePath();
            return new ListenVoice(button, fileName, directoryFinal);
        }

        private void updatePeer() {
            new UpdatePeerTask().execute(mPeer);
        }

        private void next() {
            if (mSize > mPosition + 1) {
                mBinding.sbKnowledge.setProgress(mPeer.getKnowledge());
                mViewPager.setCurrentItem(mPosition + 1);
            } else {
                getActivity().finish();
            }
        }

        private class UpdatePeerTask extends AsyncTask<PeerEntity, Void, Void> {

            @Override
            protected Void doInBackground(PeerEntity... peer) {

                ((RecordVoiceLearning) getActivity().getApplication()).getDatabase()
                        .peerDao()
                        .updatePeersSync(peer);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                next();
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a ListenPeerFragment (defined as a static inner class below).
            return ListenPeerFragment.newInstance(mPeers.get(position), position, mPeers.size(), mQuestionToAnswer);
        }

        @Override
        public int getCount() {
            return mPeers.size();
        }
    }

    private class LoadFolderPeersTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            mPeers = ((RecordVoiceLearning) getApplication()).getDatabase()
                    .peerDao()
                    .loadAllPeerByFolderIdSync(integers[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            init();
        }
    }
}
