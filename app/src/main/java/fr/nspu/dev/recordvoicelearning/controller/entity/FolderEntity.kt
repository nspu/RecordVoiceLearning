package fr.nspu.dev.recordvoicelearning.controller.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import java.io.Serializable
import java.util.Date

import fr.nspu.dev.recordvoicelearning.model.Folder
import fr.nspu.dev.recordvoicelearning.utils.OrderPeerEnum
import fr.nspu.dev.recordvoicelearning.utils.QuestionToAnswerEnum

/**
 * Created by nspu on 18-02-02.
 */

@Entity(tableName = "folders")
data class FolderEntity(
        @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) override var id: Int? ,
        @ColumnInfo(name = "name") override var name: String?,
        @ColumnInfo(name = "type_question") override var typeQuestion: String?,
        @ColumnInfo(name = "type_answer") override var typeAnswer: String?
        ) : Folder, Serializable {

    @ColumnInfo(name = "order")
    override var order: OrderPeerEnum? = OrderPeerEnum.KNOWLEDGE_ASCENDING

    @ColumnInfo(name = "question_to_answer")
    override var questionToAnswer: QuestionToAnswerEnum? = QuestionToAnswerEnum.QUESTION_TO_ANSWER

    @ColumnInfo(name = "created_at")
    override var createdAt: Date? = null

    @ColumnInfo(name = "updated_at")
    override var updatedAt: Date? = null


    constructor() : this(0, "", "", "") {}

}
