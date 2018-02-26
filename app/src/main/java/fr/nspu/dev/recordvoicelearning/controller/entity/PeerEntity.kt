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

@Entity(tableName = "peers",
        foreignKeys = arrayOf(ForeignKey(entity = FolderEntity::class,
                parentColumns = arrayOf("id"), childColumns = arrayOf("folder_Id"),
                onDelete = ForeignKey.CASCADE)),
        indices = arrayOf(Index(value = "folder_Id")))
class PeerEntity() : Peer, Serializable {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    override var id: Int? = null

    @ColumnInfo(name = "folder_Id")
    override var folderId: Int? = null

    @ColumnInfo(name = "file_name_question")
    override var fileNameQuestion: String? = null

    @ColumnInfo(name = "file_name_answer")
    override var fileNameAnswer: String? = null

    @ColumnInfo(name = "knowledge")
    override var knowledge: Int? = 0
        set(value) {
            if (value!! > 10) {
                field = 10
            } else if (value < 0) {
                field = 0
            } else {
                field = value
            }
        }

    @ColumnInfo(name = "count")
    override var count: Int? = 0

    @ColumnInfo(name = "created_at")
    override var createdAt: Date? = Date()

    @ColumnInfo(name = "updated_at")
    override var updatedAt: Date? = Date()


    fun increaseKnowledge() {
        if (knowledge!!.compareTo(10) == -1) {
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

    constructor(_idFolder: Int?) : this() {
        folderId = _idFolder
    }

    constructor(_id: Int, _idFolder: Int, _fileNameQuestion: String, _fileNameAnswer: String, _knowledge: Int, _count: Int) : this(_idFolder) {
        id = _id
        fileNameQuestion = _fileNameQuestion
        fileNameAnswer = _fileNameAnswer
        knowledge = _knowledge
        count = _count
    }
}
