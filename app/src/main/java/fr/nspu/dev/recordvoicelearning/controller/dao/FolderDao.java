package fr.nspu.dev.recordvoicelearning.controller.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.nspu.dev.recordvoicelearning.controller.entity.FolderEntity;

/**
 * Created by nspu on 18-02-02.
 */

@Dao
public interface FolderDao {
    //sync
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFoldersSync(List<FolderEntity> folders);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFolderSync(FolderEntity folder);
    @Update
    void updateFoldersSync(FolderEntity... folders);
    @Delete
    void deleteFolders(FolderEntity... folders);
    @Query("SELECT * FROM folders WHERE id = :id")
    FolderEntity loadFolderByIdSync(int id);
    @Query("SELECT * FROM folders")
    FolderEntity[] loadAllFoldersArraySync();
    @Query("SELECT * FROM folders")
    List<FolderEntity> loadAllFoldersSync();


    //async
    @Query("SELECT * FROM folders WHERE id = :id")
    LiveData<FolderEntity> loadFolderById(int id);
    @Query("SELECT * FROM folders")
    LiveData<List<FolderEntity>> loadAllFolders();
}
