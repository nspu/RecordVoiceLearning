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

import java.util.List;

import fr.nspu.dev.recordvoicelearning.RecordVoiceLearning;
import fr.nspu.dev.recordvoicelearning.R;
import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity;
import fr.nspu.dev.recordvoicelearning.databinding.FragmentListenPeersBinding;
import fr.nspu.dev.recordvoicelearning.utils.ListenVoice;

import static fr.nspu.dev.recordvoicelearning.view.fragment.FolderFragment.KEY_FOLDER_ID;

public class ListenPeersActivity extends AppCompatActivity {

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private static ViewPager mViewPager;

    private List<PeerEntity> mPeers;

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

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);

        new ListenPeersActivity.LoadFolderPeersTask().execute(folderId);
    }

    private void init(){
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


        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String PEER_OBJECT = "PEER_OBJECT";
        private static final String POSITION = "POSITION";
        private static final String SIZE_PEERS = "SIZE_PEERS";

        private FragmentListenPeersBinding mBinding;
        private PeerEntity mPeer;
        private int mPosition;
        private int mSize;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(PeerEntity peer, int position, int size) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putSerializable(PEER_OBJECT, peer);
            args.putInt(POSITION, position);
            args.putInt(SIZE_PEERS, size);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            mPeer = (PeerEntity) getArguments().getSerializable(PEER_OBJECT);
            mPosition = getArguments().getInt(POSITION);
            mSize = getArguments().getInt(SIZE_PEERS);
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_listen_peers, container, false);

            mBinding.tvPeerName.setText(getContext().getString(R.string.peer_idpeer, mPeer.getId()));
            mBinding.tvPositionTotal.setText(getContext().getString(R.string._slash_, mPosition+1, mSize));
            mBinding.sbKnowledge.setProgress(mPeer.getKnowledge());


            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            boolean isAutoPlay = sharedPreferences.getBoolean(getString(R.string.settings_auto_play), true);

            ListenVoice listenVoice = listen(mBinding.btnListenQuestion, mPeer.getFileNameQuestion());

            if(isAutoPlay){
                listenVoice.startPlaying();
            }

            listen(mBinding.btnListenAnswer, mPeer.getFileNameAnswer());

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



        private ListenVoice listen(Button button, String fileName){
            String directoryFinal = getContext().getExternalFilesDir(null).getAbsolutePath();
            return new ListenVoice(button, fileName, directoryFinal);
        }

        private void updatePeer(){
            new UpdatePeerTask().execute(mPeer);
        }

        private void next(){
            if(mSize > mPosition+1){
                mBinding.sbKnowledge.setProgress(mPeer.getKnowledge());
                mViewPager.setCurrentItem(mPosition+1);
            }else{
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
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(mPeers.get(position), position, mPeers.size());
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
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
