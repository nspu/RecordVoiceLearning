package fr.nspu.dev.recordvoicelearning.view.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import fr.nspu.dev.recordvoicelearning.R;
import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity;
import fr.nspu.dev.recordvoicelearning.databinding.FragmentFolderBinding;
import fr.nspu.dev.recordvoicelearning.utils.callback.ClickCallback;
import fr.nspu.dev.recordvoicelearning.view.ListenPeersActivity;
import fr.nspu.dev.recordvoicelearning.view.RecordActivity;
import fr.nspu.dev.recordvoicelearning.view.adapter.PeerAdapter;
import fr.nspu.dev.recordvoicelearning.viewmodel.FolderViewModel;

public class FolderFragment extends Fragment {
    public static final String KEY_FOLDER_ID = "id_folder";


    private FragmentFolderBinding mBinding;
    private PeerAdapter mPeerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_folder, container, false);

        mBinding.btnPlayPeers.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), ListenPeersActivity.class);
            intent.putExtra(KEY_FOLDER_ID, getArguments().getInt(KEY_FOLDER_ID));
            startActivity(intent);
        });

        setHasOptionsMenu(true);


        mPeerAdapter = new PeerAdapter(mPeerClickCallback, getContext());
        mBinding.peersList.setAdapter(mPeerAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.folder_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_add_peer:
                Intent intent = new Intent(getActivity().getApplicationContext(), RecordActivity.class);
                intent.putExtra(KEY_FOLDER_ID, getArguments().getInt(KEY_FOLDER_ID));
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FolderViewModel.Factory factory = new FolderViewModel.Factory(
                getActivity().getApplication(), getArguments().getInt(KEY_FOLDER_ID));

        final FolderViewModel model = ViewModelProviders.of(this, factory)
                .get(FolderViewModel.class);

        mBinding.setFolderViewModel(model);

        subscribeToModel(model);
    }

    private void subscribeToModel(final FolderViewModel model) {

        // Observe folder data
        model.getObservableFolder().observe(this, model::setFolder);

        // Observe peers
        model.getPeers().observe(this, peerEntities -> {
            if (peerEntities != null) {
                mBinding.setIsLoading(false);
                mPeerAdapter.setPeerList(peerEntities);
                mBinding.setCount(peerEntities.size());
                if(peerEntities.size() > 0){
                    mBinding.btnPlayPeers.setEnabled(true);
                }else{
                    mBinding.btnPlayPeers.setEnabled(false);
                }
            } else {
                mBinding.setIsLoading(true);
            }
        });
    }

    private final ClickCallback mPeerClickCallback = new ClickCallback() {
        @Override
        public void onClick(Object o) {
            PeerEntity peer = (PeerEntity) o;
            Toast.makeText(getContext(), Integer.toString(peer.getKnowledge()), Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onLongClick(Object o) {
            PeerEntity peer = (PeerEntity) o;
            return false;
        }
    };

    public static FolderFragment forFolder(int idFolder) {
        FolderFragment fragment = new FolderFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_FOLDER_ID, idFolder);
        fragment.setArguments(args);
        return fragment;
    }
}
