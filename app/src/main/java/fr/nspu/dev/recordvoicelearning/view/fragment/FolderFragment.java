package fr.nspu.dev.recordvoicelearning.view.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
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
import android.widget.ToggleButton;

import java.util.Collections;

import fr.nspu.dev.recordvoicelearning.R;
import fr.nspu.dev.recordvoicelearning.RecordVoiceLearning;
import fr.nspu.dev.recordvoicelearning.controller.entity.FolderEntity;
import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity;
import fr.nspu.dev.recordvoicelearning.databinding.FragmentFolderBinding;
import fr.nspu.dev.recordvoicelearning.utils.OrderPeerEnum;
import fr.nspu.dev.recordvoicelearning.utils.QuestionToAnswerEnum;
import fr.nspu.dev.recordvoicelearning.utils.callback.ClickCallback;
import fr.nspu.dev.recordvoicelearning.view.activity.ListenPeersActivity;
import fr.nspu.dev.recordvoicelearning.view.activity.RecordActivity;
import fr.nspu.dev.recordvoicelearning.view.adapter.PeerAdapter;
import fr.nspu.dev.recordvoicelearning.viewmodel.FolderViewModel;

public class FolderFragment extends Fragment {
    public static final String KEY_FOLDER_ID = "id_folder";
    public static final String KEY_ORDER = "ordre_peer";
    public static final String KEY_QUESTION_TO_ANSWER = "question_to_anwser";

    private FragmentFolderBinding mBinding;
    private PeerAdapter mPeerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_folder, container, false);
        mPeerAdapter = new PeerAdapter(mPeerClickCallback, getContext());
        mBinding.peersList.setAdapter(mPeerAdapter);

        mBinding.btnPlayPeers.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), ListenPeersActivity.class);
            intent.putExtra(KEY_FOLDER_ID, getArguments().getInt(KEY_FOLDER_ID));
            FolderEntity folderEntity = mBinding.getFolderViewModel().folder.get();
            if(folderEntity != null){
                intent.putExtra(KEY_ORDER, folderEntity.getOrder().toInt());
                intent.putExtra(KEY_QUESTION_TO_ANSWER, folderEntity.getQuestionToAnswer().toBoolean());
            }else{
                intent.putExtra(KEY_ORDER, OrderPeerEnum.KNOWLEDGE_ASCENDING.toInt());
                intent.putExtra(KEY_QUESTION_TO_ANSWER, QuestionToAnswerEnum.QUESTION_TO_ANSWER.toBoolean());
            }

            startActivity(intent);
        });

        mBinding.btnOrder.setOnClickListener(v -> {
            FolderEntity folderEntity = mBinding.getFolderViewModel().folder.get();
            boolean isChecked = ((ToggleButton)v).isChecked();
            if(folderEntity != null && isChecked){
                folderEntity.setOrder(OrderPeerEnum.KNOWLEDGE_ASCENDING);
                new UpdateFolderAsync().execute(folderEntity);
            }else{
                folderEntity.setOrder(OrderPeerEnum.KNOWLEDGE_DESCENDING);
                new UpdateFolderAsync().execute(folderEntity);
            }
        });


        mBinding.btnQuestionToAnswer.setOnCheckedChangeListener((buttonView, isChecked) -> {
            FolderEntity folderEntity = mBinding.getFolderViewModel().folder.get();
            if(folderEntity != null && isChecked){
                folderEntity.setQuestionToAnswer(QuestionToAnswerEnum.QUESTION_TO_ANSWER);
                new UpdateFolderAsync().execute(folderEntity);
            }else{
                folderEntity.setQuestionToAnswer(QuestionToAnswerEnum.ANSWER_TO_QUESTION);
                new UpdateFolderAsync().execute(folderEntity);
            }
        });



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

        model.getObservableFolder().observe(this, folderEntity -> {
            // Observe peers
            model.getPeers().observe(this, peerEntities -> {
                if (peerEntities != null) {
                    mBinding.setIsLoading(false);
                    if(folderEntity.getOrder() == OrderPeerEnum.KNOWLEDGE_ASCENDING){
                        Collections.sort(peerEntities, (o1, o2) -> Integer.compare(o1.getKnowledge(), o2.getKnowledge()));
                    }else{
                        Collections.sort(peerEntities, (o1, o2) -> Integer.compare(o2.getKnowledge(), o1.getKnowledge()));
                    }
                    mPeerAdapter.setPeerList(peerEntities);
                    //notify to display the correct order
                    mPeerAdapter.notifyDataSetChanged();
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

    public static FolderFragment forFolder(int idFolder, QuestionToAnswerEnum questionToAnswer) {
        FolderFragment fragment = new FolderFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_FOLDER_ID, idFolder);
        args.putBoolean(KEY_QUESTION_TO_ANSWER, questionToAnswer.toBoolean());
        fragment.setArguments(args);
        return fragment;
    }

    private class UpdateFolderAsync extends AsyncTask<FolderEntity, Void, Void> {
        @Override
        protected Void doInBackground(FolderEntity... folderEntities) {
            int a = ((RecordVoiceLearning) getActivity().getApplication())
                    .getDatabase()
                    .folderDao()
                    .updateFoldersSync(folderEntities[0]);
            return null;
        }
    }


}
