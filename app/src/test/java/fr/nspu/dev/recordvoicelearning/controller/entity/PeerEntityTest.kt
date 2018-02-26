package fr.nspu.dev.recordvoicelearning.controller.entity

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by nspu on 18-02-22.
 */
class PeerEntityTest {

    internal var mPeerEntity: PeerEntity? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mPeerEntity = PeerEntity()
    }

    @Test
    @Throws(Exception::class)
    fun setKnowledge() {
        mPeerEntity!!.knowledge = 5
        assertEquals(mPeerEntity!!.knowledge!!.toInt().toLong(), 5)

        //test < 10
        mPeerEntity!!.knowledge = 15
        assertEquals(mPeerEntity!!.knowledge!!.toInt().toLong(), 10)

        //test > 0
        mPeerEntity!!.knowledge = -15
        assertEquals(mPeerEntity!!.knowledge!!.toInt().toLong(), 0)
    }

    @Test
    @Throws(Exception::class)
    fun increaseKnowledge() {

        mPeerEntity!!.knowledge = 0
        mPeerEntity!!.increaseKnowledge()
        assertEquals(mPeerEntity!!.knowledge!!.toInt().toLong(), 1)

        //test when increase is at max
        mPeerEntity!!.knowledge = 10
        mPeerEntity!!.increaseKnowledge()
        assertEquals(mPeerEntity!!.knowledge!!.toInt().toLong(), 10)

        //test when increase is at max
        mPeerEntity!!.knowledge = 9
        mPeerEntity!!.increaseKnowledge()
        assertEquals(mPeerEntity!!.knowledge!!.toInt().toLong(), 10)

    }

    @Test
    @Throws(Exception::class)
    fun descreaseKnowledge() {
        mPeerEntity!!.knowledge = 2
        mPeerEntity!!.increaseKnowledge()
        assertEquals(mPeerEntity!!.knowledge!!.toInt().toLong(), 3)


        //test when increase is at max
        mPeerEntity!!.knowledge = 10
        mPeerEntity!!.increaseKnowledge()
        assertEquals(mPeerEntity!!.knowledge!!.toInt().toLong(), 10)

        //test when increase is at max
        mPeerEntity!!.knowledge = 9
        mPeerEntity!!.increaseKnowledge()
        assertEquals(mPeerEntity!!.knowledge!!.toInt().toLong(), 10)
    }
}