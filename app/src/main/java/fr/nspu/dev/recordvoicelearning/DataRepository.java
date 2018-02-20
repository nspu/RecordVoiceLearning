package fr.nspu.dev.recordvoicelearning;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;


import java.util.List;

import fr.nspu.dev.recordvoicelearning.controller.AppDatabase;
import fr.nspu.dev.recordvoicelearning.controller.entity.FolderEntity;
import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity;

/**
 * Created by nspu on 18-02-04.
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private final MediatorLiveData<List<FolderEntity>> mObservableFolders;


    private DataRepository(final AppDatabase database) {
        mDatabase = database;

        mObservableFolders = new MediatorLiveData<>();

        mObservableFolders.addSource(mDatabase.folderDao().loadAllFolders(),
                folderEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableFolders.postValue(folderEntities);
                    }
                });

    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of folders from the database and get notified when the data changes.
     */
    public LiveData<List<FolderEntity>> getFolders() {
        return mObservableFolders;
    }

    public LiveData<FolderEntity> loadFolder(final int folderId) {
        return mDatabase.folderDao().loadFolderById(folderId);
    }

    public LiveData<List<PeerEntity>> loadPeers(final int folderId) {
        return mDatabase.peerDao().loadPeerByFolderId(folderId);
    }
}
