package fr.nspu.dev.recordvoicelearning.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import fr.nspu.dev.recordvoicelearning.RecordVoiceLearning;
import fr.nspu.dev.recordvoicelearning.controller.entity.FolderEntity;

/**
 * Created by nspu on 18-02-05.
 */

public class FolderListViewModel extends AndroidViewModel {
    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<FolderEntity>> mObservableFolder;


    public FolderListViewModel(@NonNull Application application) {
        super(application);

        mObservableFolder = new MediatorLiveData<>();

        //set by default null, until we get data from the database.
        mObservableFolder.setValue(null);

        LiveData<List<FolderEntity>> folders = ((RecordVoiceLearning) application).getRepository().getFolders();

        // observe the changes of the products from the database and forward them
        mObservableFolder.addSource(folders, mObservableFolder::setValue);
    }

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<FolderEntity>> getFolders() {
        return mObservableFolder;
    }


}
