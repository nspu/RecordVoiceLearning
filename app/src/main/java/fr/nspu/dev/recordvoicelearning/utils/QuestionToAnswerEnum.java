package fr.nspu.dev.recordvoicelearning.utils;

/**
 * Created by nspu on 18-02-23.
 */

public enum QuestionToAnswerEnum {

    QUESTION_TO_ANSWER(true),
    ANSWER_TO_QUESTION(false);

    Boolean questionToAnswer = true;


    QuestionToAnswerEnum(boolean b) {
        questionToAnswer = b;
    }

    public boolean toBoolean() {
        return questionToAnswer;
    }

    public static QuestionToAnswerEnum toQuestionToAnswerEnum(Boolean b) {
        if (b == null || b) {
            return QUESTION_TO_ANSWER;
        } else {
            return ANSWER_TO_QUESTION;
        }
    }
}
