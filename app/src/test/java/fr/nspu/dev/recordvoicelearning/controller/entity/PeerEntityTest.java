package fr.nspu.dev.recordvoicelearning.controller.entity;

import org.junit.Before;
import org.junit.Test;

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
    public void setKnowledge() throws Exception {
        mPeerEntity.setKnowledge(5);
        assertEquals(mPeerEntity.getKnowledge().intValue(), 5);

        //test < 10
        mPeerEntity.setKnowledge(15);
        assertEquals(mPeerEntity.getKnowledge().intValue(), 10);

        //test > 0
        mPeerEntity.setKnowledge(-15);
        assertEquals(mPeerEntity.getKnowledge().intValue(), 0);
    }

    @Test
    public void increaseKnowledge() throws Exception {

        mPeerEntity.setKnowledge(0);
        mPeerEntity.increaseKnowledge();
        assertEquals(mPeerEntity.getKnowledge().intValue(), 1);

        //test when increase is at max
        mPeerEntity.setKnowledge(10);
        mPeerEntity.increaseKnowledge();
        assertEquals(mPeerEntity.getKnowledge().intValue(), 10);

        //test when increase is at max
        mPeerEntity.setKnowledge(9);
        mPeerEntity.increaseKnowledge();
        assertEquals(mPeerEntity.getKnowledge().intValue(), 10);

    }

    @Test
    public void descreaseKnowledge() throws Exception {
        mPeerEntity.setKnowledge(2);
        mPeerEntity.increaseKnowledge();
        assertEquals(mPeerEntity.getKnowledge().intValue(), 3);


        //test when increase is at max
        mPeerEntity.setKnowledge(10);
        mPeerEntity.increaseKnowledge();
        assertEquals(mPeerEntity.getKnowledge().intValue(), 10);

        //test when increase is at max
        mPeerEntity.setKnowledge(9);
        mPeerEntity.increaseKnowledge();
        assertEquals(mPeerEntity.getKnowledge().intValue(), 10);
    }
}