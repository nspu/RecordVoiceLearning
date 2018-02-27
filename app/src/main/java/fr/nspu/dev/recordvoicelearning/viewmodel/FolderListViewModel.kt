package fr.nspu.dev.recordvoicelearning.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData

import fr.nspu.dev.recordvoicelearning.RecordVoiceLearning
import fr.nspu.dev.recordvoicelearning.controller.entity.FolderEntity

/**
 * Created by nspu on 18-02-05.
 */

class FolderListViewModel(application: Application) : AndroidViewModel(application) {
    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private val mObservableFolder: MediatorLiveData<List<FolderEntity>>

    /**
     * Expose the LiveData folders query so the UI can observe it.
     */
    val folders: LiveData<List<FolderEntity>>
        get() = mObservableFolder


    init {

        mObservableFolder = MediatorLiveData()

        //set by default null, until we get data from the database.
        mObservableFolder.value = null

        val folders = (application as RecordVoiceLearning).repository.folders

        // observe the changes of the folders from the database and forward them
        mObservableFolder.addSource(folders, Observer<List<FolderEntity>> { mObservableFolder.setValue(it) })
    }


}
