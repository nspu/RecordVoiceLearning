package fr.nspu.dev.recordvoicelearning.view.fragment;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import fr.nspu.dev.recordvoicelearning.RecordVoiceLearning;
import fr.nspu.dev.recordvoicelearning.R;
import fr.nspu.dev.recordvoicelearning.controller.entity.FolderEntity;
import fr.nspu.dev.recordvoicelearning.databinding.FragmentAddFolderBinding;

public class AddFolderFragment extends Fragment {

    public static final String TAG = "AddFolderFragment";

    private FragmentAddFolderBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_folder, container, false);

        setHasOptionsMenu(true);

        return mBinding.getRoot();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_folder_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_validate_add_folder:
                FolderEntity folderEntity = new FolderEntity();
                folderEntity.setName(mBinding.addNameEt.getText().toString());
                folderEntity.setTypeQuestion(mBinding.addTypeQuestionEt.getText().toString());
                folderEntity.setTypeAnswer(mBinding.addTypeAnswerEt.getText().toString());
                new DatabaseAsync().execute(folderEntity);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class DatabaseAsync extends AsyncTask<FolderEntity, Void, Void> {

        @Override
        protected Void doInBackground(FolderEntity... folderEntities) {
            ((RecordVoiceLearning) getActivity().getApplication()).getDatabase().folderDao().insertFolderSync(folderEntities[0]);
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Snackbar.make(AddFolderFragment.this.getActivity().findViewById(R.id.fragment_container), R.string.folder_add, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();

            AddFolderFragment.this.getFragmentManager().popBackStack();
        }
    }

}
