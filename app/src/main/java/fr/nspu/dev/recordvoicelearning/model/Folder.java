package fr.nspu.dev.recordvoicelearning.model;

import fr.nspu.dev.recordvoicelearning.utils.OrderPeerEnum;
import fr.nspu.dev.recordvoicelearning.utils.QuestionToAnswerEnum;

/**
 * Created by nspu on 18-02-03.
 */



public interface Folder extends CollectionObject{
    String getName();
    String getTypeQuestion();
    String getTypeAnswer();
    OrderPeerEnum getOrder();
    QuestionToAnswerEnum getQuestionToAnswer();
}
