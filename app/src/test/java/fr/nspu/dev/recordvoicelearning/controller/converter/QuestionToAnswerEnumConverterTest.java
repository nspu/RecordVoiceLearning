package fr.nspu.dev.recordvoicelearning.controller.converter;

import org.junit.Test;

import fr.nspu.dev.recordvoicelearning.utils.QuestionToAnswerEnum;

import static org.junit.Assert.*;

/**
 * Created by nspu on 18-02-23.
 */
public class QuestionToAnswerEnumConverterTest {
    @Test
    public void toQuestionToAnswerEnum() throws Exception {
        QuestionToAnswerEnum questionToAnswerEnumTrue = QuestionToAnswerEnumConverter.toQuestionToAnswerEnum(true);
        QuestionToAnswerEnum questionToAnswerEnumFalse = QuestionToAnswerEnumConverter.toQuestionToAnswerEnum(false);

        assertEquals(questionToAnswerEnumTrue, QuestionToAnswerEnum.QUESTION_TO_ANSWER);
        assertEquals(questionToAnswerEnumFalse, QuestionToAnswerEnum.ANSWER_TO_QUESTION);
    }

    @Test
    public void toBoolean() throws Exception {
        boolean bTrue = QuestionToAnswerEnumConverter.toBoolean(QuestionToAnswerEnum.QUESTION_TO_ANSWER);
        boolean bFalse = QuestionToAnswerEnumConverter.toBoolean(QuestionToAnswerEnum.ANSWER_TO_QUESTION);
        boolean bDefault = QuestionToAnswerEnumConverter.toBoolean(null);

        assertEquals(bTrue, true);
        assertEquals(bFalse, false);
        assertEquals(bDefault, true);
    }

}