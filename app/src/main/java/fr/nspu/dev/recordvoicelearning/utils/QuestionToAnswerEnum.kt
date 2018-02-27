package fr.nspu.dev.recordvoicelearning.utils

/**
 * Created by nspu on 18-02-23.
 */

enum class QuestionToAnswerEnum private constructor(b: Boolean) {

    QUESTION_TO_ANSWER(true),
    ANSWER_TO_QUESTION(false);

    internal var questionToAnswer: Boolean? = true


    init {
        questionToAnswer = b
    }

    fun toBoolean(): Boolean = questionToAnswer!!

    companion object {
        fun toQuestionToAnswerEnum(b: Boolean?): QuestionToAnswerEnum? = if (b == null || b) QUESTION_TO_ANSWER else ANSWER_TO_QUESTION
    }
}
