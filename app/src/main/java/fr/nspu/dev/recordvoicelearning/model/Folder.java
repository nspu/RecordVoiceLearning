package fr.nspu.dev.recordvoicelearning.model;

/**
 * Created by nspu on 18-02-03.
 */



public interface Folder extends CollectionObject{
    String getName();
    String getTypeQuestion();
    String getTypeAnswer();
    int getOrder();
    boolean isQuestionToAnswer();

    //Order
    int ORDER_KNOWLEDGE_ASCENDING = 0;
    int ORDER_KNOWLEDGE_DESCENDING = 1;

    //QuestionToAnswer
    boolean QUESTION_TO_ANSWER = true;
    boolean ANSWER_TO_QUESTION = false;
}
