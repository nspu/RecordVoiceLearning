package fr.nspu.dev.recordvoicelearning.controller.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

import java.io.Serializable
import java.util.Date

import fr.nspu.dev.recordvoicelearning.model.Folder
import fr.nspu.dev.recordvoicelearning.utils.OrderPeerEnum
import fr.nspu.dev.recordvoicelearning.utils.QuestionToAnswerEnum

/**
 * Created by nspu on 18-02-02.
 */

@Entity(tableName = "folders")
class FolderEntity() : Folder {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    override var id: Int? = null

    @ColumnInfo(name = "name")
    override var name: String? = null

    @ColumnInfo(name = "type_question")
    override var typeQuestion: String? = null

    @ColumnInfo(name = "type_answer")
    override var typeAnswer: String? = null

    @ColumnInfo(name = "order")
    override var order: OrderPeerEnum = OrderPeerEnum.KNOWLEDGE_ASCENDING

    @ColumnInfo(name = "question_to_answer")
    override var questionToAnswer: QuestionToAnswerEnum = QuestionToAnswerEnum.QUESTION_TO_ANSWER

    @ColumnInfo(name = "created_at")
    override var createdAt: Date? = null

    @ColumnInfo(name = "updated_at")
    override var updatedAt: Date? = null

    constructor(_id: Int, _name: String, _typeQuestion: String, _typeAnswer: String) : this() {
        id = _id
        name = _name
        typeQuestion = _typeQuestion
        typeAnswer = _typeAnswer
    }
}
