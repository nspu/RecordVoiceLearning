package fr.nspu.dev.recordvoicelearning.controller.converter

import org.junit.Test

import fr.nspu.dev.recordvoicelearning.utils.QuestionToAnswerEnum

import org.junit.Assert.*

/**
 * Created by nspu on 18-02-23.
 */
class QuestionToAnswerEnumConverterTest {
    @Test
    @Throws(Exception::class)
    fun toQuestionToAnswerEnum() {
        val questionToAnswerEnumTrue = QuestionToAnswerEnumConverter.toQuestionToAnswerEnum(true)
        val questionToAnswerEnumFalse = QuestionToAnswerEnumConverter.toQuestionToAnswerEnum(false)

        assertEquals(questionToAnswerEnumTrue, QuestionToAnswerEnum.QUESTION_TO_ANSWER)
        assertEquals(questionToAnswerEnumFalse, QuestionToAnswerEnum.ANSWER_TO_QUESTION)
    }

    @Test
    @Throws(Exception::class)
    fun toBoolean() {
        val bTrue = QuestionToAnswerEnumConverter.toBoolean(QuestionToAnswerEnum.QUESTION_TO_ANSWER)!!
        val bFalse = QuestionToAnswerEnumConverter.toBoolean(QuestionToAnswerEnum.ANSWER_TO_QUESTION)!!
        val bDefault = QuestionToAnswerEnumConverter.toBoolean(null)!!

        assertEquals(bTrue, true)
        assertEquals(bFalse, false)
        assertEquals(bDefault, true)
    }

}