package fr.nspu.dev.recordvoicelearning.controller.entity;

import org.junit.Before;
import org.junit.Test;

import fr.nspu.dev.recordvoicelearning.model.Peer;

import static org.junit.Assert.*;

/**
 * Created by nspu on 18-02-22.
 */
public class PeerEntityTest {

    PeerEntity mPeerEntity;

    @Before
    public void setUp() throws Exception {
        mPeerEntity = new PeerEntity();
    }

    @Test
    public void setId() throws Exception {
        long updated = mPeerEntity.getUpdatedAt().getTime();
        //wait 5 ms to be sure that time will be different for the update
        Thread.sleep(5);
        mPeerEntity.setId(1);
        assertEquals(mPeerEntity.getId(), 1);
        assertTrue(mPeerEntity.getUpdatedAt().getTime() > updated);
    }

    @Test
    public void setFolderId() throws Exception {
        long updated = mPeerEntity.getUpdatedAt().getTime();
        //wait 5 ms to be sure that time will be different for the update
        Thread.sleep(5);
        mPeerEntity.setFolderId(1);
        assertEquals(mPeerEntity.getFolderId(), 1);
        assertTrue(mPeerEntity.getUpdatedAt().getTime() > updated);
    }

    @Test
    public void setFileNameQuestion() throws Exception {
        long updated = mPeerEntity.getUpdatedAt().getTime();
        //wait 5 ms to be sure that time will be different for the update
        Thread.sleep(5);
        mPeerEntity.setFileNameQuestion("Abc");
        assertEquals(mPeerEntity.getFileNameQuestion(), "Abc");
        assertTrue(mPeerEntity.getUpdatedAt().getTime() > updated);
    }

    @Test
    public void setFileNameAnswer() throws Exception {
        long updated = mPeerEntity.getUpdatedAt().getTime();
        //wait 5 ms to be sure that time will be different for the update
        Thread.sleep(5);
        mPeerEntity.setFileNameAnswer("Abcd");
        assertEquals(mPeerEntity.getFileNameAnswer(), "Abcd");
        assertTrue(mPeerEntity.getUpdatedAt().getTime() > updated);
    }

    @Test
    public void setCount() throws Exception {
        long updated = mPeerEntity.getUpdatedAt().getTime();
        //wait 5 ms to be sure that time will be different for the update
        Thread.sleep(5);
        mPeerEntity.setCount(6);
        assertEquals(mPeerEntity.getCount(), 6);
        assertTrue(mPeerEntity.getUpdatedAt().getTime() > updated);
    }

    @Test
    public void setKnowledge() throws Exception {
        long updated = mPeerEntity.getUpdatedAt().getTime();
        //wait 5 ms to be sure that time will be different for the update
        Thread.sleep(5);
        mPeerEntity.setKnowledge(5);
        assertEquals(mPeerEntity.getKnowledge(), 5);
        assertTrue(mPeerEntity.getUpdatedAt().getTime() > updated);

        //test < 10
        mPeerEntity.setKnowledge(15);
        assertEquals(mPeerEntity.getKnowledge(), 10);

        //test > 0
        mPeerEntity.setKnowledge(-15);
        assertEquals(mPeerEntity.getKnowledge(), 0);
    }

    @Test
    public void increaseKnowledge() throws Exception {
        long updated =0;

        mPeerEntity.setKnowledge(0);
        updated = mPeerEntity.getUpdatedAt().getTime();

        //wait 5 ms to be sure that time will be different for the update
        Thread.sleep(5);
        mPeerEntity.increaseKnowledge();
        assertEquals(mPeerEntity.getKnowledge(), 1);
        assertTrue(mPeerEntity.getUpdatedAt().getTime() > updated);


        //test when increase is at max
        mPeerEntity.setKnowledge(10);
        mPeerEntity.increaseKnowledge();
        assertEquals(mPeerEntity.getKnowledge(), 10);

        //test when increase is at max
        mPeerEntity.setKnowledge(9);
        mPeerEntity.increaseKnowledge();
        assertEquals(mPeerEntity.getKnowledge(), 10);

    }

    @Test
    public void descreaseKnowledge() throws Exception {
        long updated =0;

        mPeerEntity.setKnowledge(2);
        updated = mPeerEntity.getUpdatedAt().getTime();

        //wait 5 ms to be sure that time will be different for the update
        Thread.sleep(5);
        mPeerEntity.increaseKnowledge();
        assertEquals(mPeerEntity.getKnowledge(), 3);
        assertTrue(mPeerEntity.getUpdatedAt().getTime() > updated);


        //test when increase is at max
        mPeerEntity.setKnowledge(10);
        mPeerEntity.increaseKnowledge();
        assertEquals(mPeerEntity.getKnowledge(), 10);

        //test when increase is at max
        mPeerEntity.setKnowledge(9);
        mPeerEntity.increaseKnowledge();
        assertEquals(mPeerEntity.getKnowledge(), 10);
    }
}