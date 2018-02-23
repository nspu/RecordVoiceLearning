package fr.nspu.dev.recordvoicelearning.model;

import fr.nspu.dev.recordvoicelearning.utils.OrderPeerEnum;

/**
 * Created by nspu on 18-02-03.
 */



public interface Folder extends CollectionObject{
    String getName();
    String getTypeQuestion();
    String getTypeAnswer();
    OrderPeerEnum getOrder();
    boolean isQuestionToAnswer();

    //QuestionToAnswer
    boolean QUESTION_TO_ANSWER = true;
    boolean ANSWER_TO_QUESTION = false;
}
