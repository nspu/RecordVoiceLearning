package fr.nspu.dev.recordvoicelearning.utils

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by nspu on 24/02/18.
 */
class QuestionToAnswerEnumTest {
    @Test
    @Throws(Exception::class)
    fun toBoolean() {
        val bTrue = QuestionToAnswerEnum.QUESTION_TO_ANSWER.toBoolean()
        val bFalse = QuestionToAnswerEnum.ANSWER_TO_QUESTION.toBoolean()

        assertEquals(bTrue, true)
        assertEquals(bFalse, false)
    }

    @Test
    @Throws(Exception::class)
    fun toQuestionToAnswerEnum() {
        val questionToAnswerEnumTrue = QuestionToAnswerEnum.toQuestionToAnswerEnum(true)
        val questionToAnswerEnumFalse = QuestionToAnswerEnum.toQuestionToAnswerEnum(false)

        assertEquals(questionToAnswerEnumTrue, QuestionToAnswerEnum.QUESTION_TO_ANSWER)
        assertEquals(questionToAnswerEnumFalse, QuestionToAnswerEnum.ANSWER_TO_QUESTION)
    }

}