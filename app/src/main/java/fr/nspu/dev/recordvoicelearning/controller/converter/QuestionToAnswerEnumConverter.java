package fr.nspu.dev.recordvoicelearning.controller.converter;

import android.arch.persistence.room.TypeConverter;

import fr.nspu.dev.recordvoicelearning.utils.QuestionToAnswerEnum;

/**
 * Created by nspu on 18-02-23.
 */

public class QuestionToAnswerEnumConverter {
    @TypeConverter
    public static QuestionToAnswerEnum toQuestionToAnswerEnum(boolean b) {
        return QuestionToAnswerEnum.toQuestionToAnswerEnum(b);
    }

    @TypeConverter
    public static boolean toBoolean(QuestionToAnswerEnum questionToAnswerEnum) {
        return questionToAnswerEnum == null ? true : questionToAnswerEnum.toBoolean();
    }
}
