package fr.nspu.dev.recordvoicelearning.controller.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity

/**
 * Created by nspu on 18-02-02.
 */

@Dao
interface PeerDao {
    //sync
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPeersSync(peers: List<PeerEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPeerSync(peer: PeerEntity)

    @Update
    fun updatePeersSync(vararg peers: PeerEntity)

    @Delete
    fun deletePeersSync(vararg peers: PeerEntity)

    @Query("SELECT * FROM peers WHERE folder_Id = :folder_id")
    fun loadAllPeersByFolderIdArraySync(folder_id: Int): Array<PeerEntity>

    @Query("SELECT * FROM peers WHERE folder_Id = :folder_id")
    fun loadAllPeerByFolderIdSync(folder_id: Int): List<PeerEntity>

    //async
    @Query("SELECT * FROM peers WHERE folder_Id = :folder_id")
    fun loadPeerByFolderId(folder_id: Int): LiveData<List<PeerEntity>>

}
