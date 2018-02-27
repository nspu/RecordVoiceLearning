package fr.nspu.dev.recordvoicelearning.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nspu on 24/02/18.
 */
public class QuestionToAnswerEnumTest {
    @Test
    public void toBoolean() throws Exception {
        boolean bTrue = QuestionToAnswerEnum.QUESTION_TO_ANSWER.toBoolean();
        boolean bFalse = QuestionToAnswerEnum.ANSWER_TO_QUESTION.toBoolean();

        assertEquals(bTrue, true);
        assertEquals(bFalse, false);
    }

    @Test
    public void toQuestionToAnswerEnum() throws Exception {
        QuestionToAnswerEnum questionToAnswerEnumTrue = QuestionToAnswerEnum.Companion.toQuestionToAnswerEnum(true);
        QuestionToAnswerEnum questionToAnswerEnumFalse = QuestionToAnswerEnum.Companion.toQuestionToAnswerEnum(false);
        
        assertEquals(questionToAnswerEnumTrue, QuestionToAnswerEnum.QUESTION_TO_ANSWER);
        assertEquals(questionToAnswerEnumFalse, QuestionToAnswerEnum.ANSWER_TO_QUESTION);
    }

}