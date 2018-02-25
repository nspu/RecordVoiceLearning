package fr.nspu.dev.recordvoicelearning.controller.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

import fr.nspu.dev.recordvoicelearning.controller.entity.FolderEntity

/**
 * Created by nspu on 18-02-02.
 */

@Dao
interface FolderDao {
    //sync
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFoldersSync(folders: List<FolderEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFolderSync(folder: FolderEntity)

    @Update
    fun updateFoldersSync(vararg folders: FolderEntity): Int

    @Delete
    fun deleteFolders(vararg folders: FolderEntity)

    @Query("SELECT * FROM folders WHERE id = :id")
    fun loadFolderByIdSync(id: Int): FolderEntity

    @Query("SELECT * FROM folders")
    fun loadAllFoldersArraySync(): Array<FolderEntity>

    @Query("SELECT * FROM folders")
    fun loadAllFoldersSync(): List<FolderEntity>


    //async
    @Query("SELECT * FROM folders WHERE id = :id")
    fun loadFolderById(id: Int): LiveData<FolderEntity>

    @Query("SELECT * FROM folders")
    fun loadAllFolders(): LiveData<List<FolderEntity>>
}


