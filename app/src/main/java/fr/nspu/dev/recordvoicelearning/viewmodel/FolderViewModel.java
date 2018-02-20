package fr.nspu.dev.recordvoicelearning.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import java.util.List;

import fr.nspu.dev.recordvoicelearning.RecordVoiceLearning;
import fr.nspu.dev.recordvoicelearning.DataRepository;
import fr.nspu.dev.recordvoicelearning.controller.entity.FolderEntity;
import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity;

/**
 * Created by nspu on 18-02-06.
 */

public class FolderViewModel extends AndroidViewModel {

    public final ObservableField<FolderEntity> folder = new ObservableField<>();

    private final LiveData<FolderEntity> mObservableFolder;
    private final LiveData<List<PeerEntity>> mObservablePeers;


    public FolderViewModel(Application application, DataRepository repository, int idFolder) {
        super(application);

        mObservableFolder = repository.loadFolder(idFolder);
        mObservablePeers = repository.loadPeers(idFolder);

    }

    /**
     * Expose the LiveData Comments query so the UI can observe it.
     */
    public LiveData<FolderEntity> getObservableFolder() {
        return mObservableFolder;
    }

    public void setFolder(FolderEntity folder) {
        this.folder.set(folder);
    }

    public LiveData<List<PeerEntity>> getPeers(){ return mObservablePeers; }

    /**
     * A creator is used to inject the product ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final int mFolderId;

        private final DataRepository mRepository;

        public Factory(@NonNull Application application, int folderId) {
            mApplication = application;
            mFolderId = folderId;
            mRepository = ((RecordVoiceLearning) application).getRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new FolderViewModel(mApplication, mRepository, mFolderId);
        }
    }
}
