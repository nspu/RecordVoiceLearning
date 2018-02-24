package fr.nspu.dev.recordvoicelearning.controller.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import fr.nspu.dev.recordvoicelearning.model.Folder;
import fr.nspu.dev.recordvoicelearning.utils.OrderPeerEnum;
import fr.nspu.dev.recordvoicelearning.utils.QuestionToAnswerEnum;

import static org.junit.Assert.*;

/**
 * Created by nspu on 18-02-22.
 */
public class FolderEntityTest {

    FolderEntity mFolderEntity;

    @Before
    public void setUp() throws Exception {
        mFolderEntity = new FolderEntity();
    }

    @Test
    public void setId() throws Exception {
        long updated = mFolderEntity.getUpdatedAt().getTime();
        //wait 5 ms to be sure that time will be different for the update
        Thread.sleep(5);
        mFolderEntity.setId(1);
        assertEquals(mFolderEntity.getId(), 1);
        assertTrue(mFolderEntity.getUpdatedAt().getTime() > updated);
    }

    @Test
    public void setName() throws Exception {
        long updated = mFolderEntity.getUpdatedAt().getTime();
        //wait 5 ms to be sure that time will be different for the update
        Thread.sleep(5);
        mFolderEntity.setName("Abc");
        assertEquals(mFolderEntity.getName(), "Abc");
        assertTrue(mFolderEntity.getUpdatedAt().getTime() > updated);
    }

    @Test
    public void setTypeQuestion() throws Exception {
        long updated = mFolderEntity.getUpdatedAt().getTime();
        //wait 5 ms to be sure that time will be different for the update
        Thread.sleep(5);
        mFolderEntity.setTypeQuestion("Abcd");
        assertEquals(mFolderEntity.getTypeQuestion(), "Abcd");
        assertTrue(mFolderEntity.getUpdatedAt().getTime() > updated);
    }

    @Test
    public void setTypeAnswer() throws Exception {
        long updated = mFolderEntity.getUpdatedAt().getTime();
        //wait 5 ms to be sure that time will be different for the update
        Thread.sleep(5);
        mFolderEntity.setTypeAnswer("Abcde");
        assertEquals(mFolderEntity.getTypeAnswer(), "Abcde");
        assertTrue(mFolderEntity.getUpdatedAt().getTime() > updated);
    }

    @Test
    public void setOrder() throws Exception {
        long updated = mFolderEntity.getUpdatedAt().getTime();
        //wait 5 ms to be sure that time will be different for the update
        Thread.sleep(5);
        mFolderEntity.setOrder(OrderPeerEnum.KNOWLEDGE_DESCENDING);
        assertEquals(mFolderEntity.getOrder(), OrderPeerEnum.KNOWLEDGE_DESCENDING);
        assertTrue(mFolderEntity.getUpdatedAt().getTime() > updated);
    }

    @Test
    public void setQuestionToAnswer() throws Exception {
        long updated = mFolderEntity.getUpdatedAt().getTime();
        //wait 5 ms to be sure that time will be different for the update
        Thread.sleep(5);
        mFolderEntity.setQuestionToAnswer(QuestionToAnswerEnum.QUESTION_TO_ANSWER);
        assertFalse(mFolderEntity.getQuestionToAnswer().toBoolean());
        assertTrue(mFolderEntity.getUpdatedAt().getTime() > updated);
    }
}