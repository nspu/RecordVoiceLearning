package fr.nspu.dev.recordvoicelearning.controller.converter

import fr.nspu.dev.recordvoicelearning.utils.QuestionToAnswerEnum
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by nspu on 18-02-23.
 */
class QuestionToAnswerEnumConverterTest {
    val questionToAnswerEnumConverter = QuestionToAnswerEnumConverter()

    @Test
    @Throws(Exception::class)
    fun toQuestionToAnswerEnum() {
        val questionToAnswerEnumTrue = questionToAnswerEnumConverter.toQuestionToAnswerEnum(true)
        val questionToAnswerEnumFalse = questionToAnswerEnumConverter.toQuestionToAnswerEnum(false)
        val questionToAnswerEnumFalseDefault = questionToAnswerEnumConverter.toQuestionToAnswerEnum(null)

        assertEquals(questionToAnswerEnumTrue, QuestionToAnswerEnum.QUESTION_TO_ANSWER)
        assertEquals(questionToAnswerEnumFalse, QuestionToAnswerEnum.ANSWER_TO_QUESTION)
        assertEquals(questionToAnswerEnumFalseDefault, QuestionToAnswerEnum.QUESTION_TO_ANSWER)
    }

    @Test
    @Throws(Exception::class)
    fun toBoolean() {
        val bTrue = questionToAnswerEnumConverter.toBoolean(QuestionToAnswerEnum.QUESTION_TO_ANSWER)
        val bFalse = questionToAnswerEnumConverter.toBoolean(QuestionToAnswerEnum.ANSWER_TO_QUESTION)

        assertEquals(bTrue, true)
        assertEquals(bFalse, false)
    }

}