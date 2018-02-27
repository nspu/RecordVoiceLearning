package fr.nspu.dev.recordvoicelearning

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData

import fr.nspu.dev.recordvoicelearning.controller.AppDatabase
import fr.nspu.dev.recordvoicelearning.controller.entity.FolderEntity
import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity

/**
 * Created by nspu on 18-02-04.
 */
class DataRepository private constructor(private val mDatabase: AppDatabase) {
    private val mObservableFolders: MediatorLiveData<List<FolderEntity>>

    /**
     * Get the list of folders from the database and get notified when the data changes.
     */
    val folders: LiveData<List<FolderEntity>>
        get() = mObservableFolders


    init {

        mObservableFolders = MediatorLiveData()

        mObservableFolders.addSource(mDatabase.folderDao().loadAllFolders()
        ) { folderEntities ->
            if (mDatabase.databaseCreated.value != null) {
                mObservableFolders.postValue(folderEntities)
            }
        }

    }

    fun loadFolder(folderId: Int): LiveData<FolderEntity> {
        return mDatabase.folderDao().loadFolderById(folderId)
    }

    fun loadPeers(folderId: Int): LiveData<List<PeerEntity>> {
        return mDatabase.peerDao().loadPeerByFolderId(folderId)
    }

    companion object {

        private var sInstance: DataRepository? = null

        fun getInstance(database: AppDatabase): DataRepository? {
            if (sInstance == null) {
                synchronized(DataRepository::class.java) {
                    if (sInstance == null) {
                        sInstance = DataRepository(database)
                    }
                }
            }
            return sInstance
        }
    }
}
