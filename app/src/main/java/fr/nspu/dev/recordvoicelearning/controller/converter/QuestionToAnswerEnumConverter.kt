package fr.nspu.dev.recordvoicelearning.controller.converter

import android.arch.persistence.room.TypeConverter

import fr.nspu.dev.recordvoicelearning.utils.QuestionToAnswerEnum

/**
 * Created by nspu on 18-02-23.
 */

object QuestionToAnswerEnumConverter {
    @TypeConverter
    fun toQuestionToAnswerEnum(b: Boolean): QuestionToAnswerEnum {
        return QuestionToAnswerEnum.toQuestionToAnswerEnum(b)
    }

    @TypeConverter
    fun toBoolean(questionToAnswerEnum: QuestionToAnswerEnum?): Boolean {
        return questionToAnswerEnum == null || questionToAnswerEnum.toBoolean()
    }
}
