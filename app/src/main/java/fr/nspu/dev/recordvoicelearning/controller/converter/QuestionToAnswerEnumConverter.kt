package fr.nspu.dev.recordvoicelearning.controller.converter

import android.arch.persistence.room.TypeConverter

import fr.nspu.dev.recordvoicelearning.utils.QuestionToAnswerEnum

/**
 * Created by nspu on 18-02-23.
 */


class QuestionToAnswerEnumConverter {
    @TypeConverter
    fun toQuestionToAnswerEnum(b: Boolean?): QuestionToAnswerEnum = QuestionToAnswerEnum.toQuestionToAnswerEnum(b)

    @TypeConverter
    fun toBoolean(questionToAnswerEnum: QuestionToAnswerEnum?): Boolean? = questionToAnswerEnum?.let { it.toBoolean() }
}

