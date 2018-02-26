package fr.nspu.dev.recordvoicelearning.controller.converter

import android.arch.persistence.room.TypeConverter

import fr.nspu.dev.recordvoicelearning.utils.QuestionToAnswerEnum

/**
 * Created by nspu on 18-02-23.
 */

<<<<<<< HEAD
object QuestionToAnswerEnumConverter {
    @TypeConverter
    fun toQuestionToAnswerEnum(b: Boolean?): QuestionToAnswerEnum
            = QuestionToAnswerEnum.toQuestionToAnswerEnum(b!!)

    @TypeConverter
    fun toBoolean(questionToAnswerEnum: QuestionToAnswerEnum?): Boolean?
            = questionToAnswerEnum?.let { questionToAnswerEnum.toBoolean() }
=======
class QuestionToAnswerEnumConverter {
    companion object {
        @TypeConverter
        fun toQuestionToAnswerEnum(b: Boolean): QuestionToAnswerEnum {
            return QuestionToAnswerEnum.toQuestionToAnswerEnum(b)
        }

        @TypeConverter
        fun toBoolean(questionToAnswerEnum: QuestionToAnswerEnum?): Boolean {
            return questionToAnswerEnum == null || questionToAnswerEnum.toBoolean()
        }
    }
>>>>>>> ff2f35cdc51ea92cf7acf28096a98e2a94103ab2
}
