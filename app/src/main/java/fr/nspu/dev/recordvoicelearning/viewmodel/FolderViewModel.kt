package fr.nspu.dev.recordvoicelearning.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.ObservableField

import fr.nspu.dev.recordvoicelearning.RecordVoiceLearning
import fr.nspu.dev.recordvoicelearning.DataRepository
import fr.nspu.dev.recordvoicelearning.controller.entity.FolderEntity
import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity

/**
 * Created by nspu on 18-02-06.
 */

class FolderViewModel(application: Application, repository: DataRepository, idFolder: Int) : AndroidViewModel(application) {

    val folder = ObservableField<FolderEntity>()

    /**
     * Expose the LiveData peers query so the UI can observe it.
     */
    val observableFolder: LiveData<FolderEntity>
    val peers: LiveData<List<PeerEntity>>

    init {

        observableFolder = repository.loadFolder(idFolder)
        peers = repository.loadPeers(idFolder)

    }

    fun setFolder(folder: FolderEntity) {
        this.folder.set(folder)
    }

    /**
     * A creator is used to inject the folder ID into the ViewModel
     *
     *
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the folder ID can be passed in a public method.
     */
    class Factory(private val mApplication: Application, private val mFolderId: Int) : ViewModelProvider.NewInstanceFactory() {

        private val mRepository: DataRepository

        init {
            mRepository = (mApplication as RecordVoiceLearning).repository
        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return FolderViewModel(mApplication, mRepository, mFolderId) as T
        }
    }
}
