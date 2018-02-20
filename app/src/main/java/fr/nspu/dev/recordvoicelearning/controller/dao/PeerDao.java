package fr.nspu.dev.recordvoicelearning.controller.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity;

/**
 * Created by nspu on 18-02-02.
 */

@Dao
public interface PeerDao {
    //sync
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPeersSync(List<PeerEntity> peers);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPeerSync(PeerEntity peer);
    @Update
    void updatePeersSync(PeerEntity... peers);
    @Delete
    void deletePeersSync(PeerEntity... peers);
    @Query("SELECT * FROM peers WHERE folder_Id = :folder_id")
    PeerEntity[] loadAllPeersByFolderIdArraySync(int folder_id);
    @Query("SELECT * FROM peers WHERE folder_Id = :folder_id")
    List<PeerEntity> loadAllPeerByFolderIdSync(int folder_id);

    //async
    @Query("SELECT * FROM peers WHERE folder_Id = :folder_id")
    LiveData<List<PeerEntity>> loadPeerByFolderId(int folder_id);

}
