package fr.nspu.dev.recordvoicelearning.controller.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

import java.io.Serializable
import java.util.Date

import fr.nspu.dev.recordvoicelearning.model.Peer

/**
 * Created by nspu on 18-02-02.
 */

@Entity(tableName = "peers", foreignKeys = arrayOf(ForeignKey(entity = FolderEntity::class, parentColumns = arrayOf("id"), childColumns = arrayOf("folder_Id"), onDelete = ForeignKey.CASCADE)), indices = arrayOf(Index(value = "folder_Id")))
class PeerEntity @Ignore
constructor(
            @ColumnInfo(name = "folder_Id") @PrimaryKey(autoGenerate = true) override var id: Int?,
            @ColumnInfo(name = "folder_Id") override var folderId: Int?,
            @ColumnInfo(name = "file_name_question") override var fileNameQuestion: String?,
            @ColumnInfo(name = "file_name_answer") override var fileNameAnswer: String?,
            @ColumnInfo(name = "knowledge") override var knowledge: Int?,
            @ColumnInfo(name = "count") override var count: Int?)
    : Peer, Serializable {

    @ColumnInfo(name = "created_at")
    override var createdAt: Date? = null

    @ColumnInfo(name = "updated_at")
    override var updatedAt: Date? = null


    fun setKnowledge(knowledge: Int) {
        if (knowledge > 10) {
            this.knowledge = 10
        } else if (knowledge < 0) {
            this.knowledge = 0
        } else {
            this.knowledge = knowledge
        }
    }


    fun increaseKnowledge() {
        if (knowledge!!.compareTo(10) == -1 ) {
            knowledge = knowledge!!.inc()
        }
        this.count = this.count!!.inc()
    }

    fun descreaseKnowledge() {
        if (knowledge!! > 0) {
            knowledge = knowledge!!.dec()
        }
            this.count = this.count!!.inc()
    }

    constructor() : this(0, 0, "", "", 0, 0) {}
}
