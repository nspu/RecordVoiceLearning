package fr.nspu.dev.recordvoicelearning.view.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import fr.nspu.dev.recordvoicelearning.RecordVoiceLearning;
import fr.nspu.dev.recordvoicelearning.R;
import fr.nspu.dev.recordvoicelearning.controller.entity.FolderEntity;
import fr.nspu.dev.recordvoicelearning.databinding.FragmentListFolderBinding;
import fr.nspu.dev.recordvoicelearning.utils.callback.ClickCallback;
import fr.nspu.dev.recordvoicelearning.view.activity.SettingsActivity;
import fr.nspu.dev.recordvoicelearning.view.activity.MainActivity;
import fr.nspu.dev.recordvoicelearning.view.adapter.FolderAdapter;
import fr.nspu.dev.recordvoicelearning.viewmodel.FolderListViewModel;

public class FolderListFragment extends Fragment {

    public static final String TAG = "FolderListViewModel";

    private FolderAdapter mFolderAdapter;

    private FragmentListFolderBinding mBinding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_folder, container, false);

        mFolderAdapter = new FolderAdapter(mFolderClickCallback);
        mBinding.foldersList.setAdapter(mFolderAdapter);
        setHasOptionsMenu(true);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        final FolderListViewModel viewModel = ViewModelProviders
                .of(this)
                .get(FolderListViewModel.class);

        subscribeUi(viewModel);

        FloatingActionButton fab = getView().findViewById(R.id.fab_add_folder);
        fab.setOnClickListener(view -> ((MainActivity)getActivity()).addFolder());

        super.onActivityCreated(savedInstanceState);
    }

    private void subscribeUi(FolderListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getFolders().observe(this, folderEntities -> {
            if (folderEntities != null) {
                mBinding.setIsLoading(false);
                mFolderAdapter.setFolderList(folderEntities);
            } else {
                mBinding.setIsLoading(true);
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings();
        });
    }

    private final ClickCallback mFolderClickCallback = new ClickCallback() {
        @Override
        public void onClick(Object o) {
            FolderEntity folder = (FolderEntity) o;
            ((MainActivity)getActivity()).showFolder(folder);
        }

        @Override
        public boolean onLongClick(Object o) {
            FolderEntity folder = (FolderEntity) o;
            new DatabaseAsync().execute((FolderEntity) folder);
            return true;
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.folder_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.settings:
                Intent intent = new Intent(getActivity().getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class DatabaseAsync extends AsyncTask<FolderEntity, Void, Void> {


        @Override
        protected Void doInBackground(FolderEntity... folderEntities) {
            ((RecordVoiceLearning) getActivity().getApplication()).getDatabase().folderDao().deleteFolders(folderEntities[0]);
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            Snackbar.make(FolderListFragment.this.getActivity().findViewById(R.id.fragment_container), R.string.folder_deleted, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            super.onPostExecute(aVoid);
        }
    }
}
