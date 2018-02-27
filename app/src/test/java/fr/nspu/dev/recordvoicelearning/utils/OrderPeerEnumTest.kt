package fr.nspu.dev.recordvoicelearning.utils

import org.junit.Test

import org.junit.Assert.*

/**
 * Created by nspu on 18-02-22.
 */
class OrderPeerEnumTest {
    @Test
    @Throws(Exception::class)
    fun toInt() {
        val i0 = OrderPeerEnum.KNOWLEDGE_ASCENDING.toInt()
        val i1 = OrderPeerEnum.KNOWLEDGE_DESCENDING.toInt()

        assertEquals(i0.toLong(), 0)
        assertEquals(i1.toLong(), 1)
    }

    @Test
    @Throws(Exception::class)
    fun toOrderPeerEnum() {
        val orderPeerEnumNotInRange = OrderPeerEnum.toOrderPeerEnum(5)
        val orderPeerEnumCorrect0 = OrderPeerEnum.toOrderPeerEnum(0)
        val orderPeerEnumCorrect1 = OrderPeerEnum.toOrderPeerEnum(1)

        assertEquals(orderPeerEnumNotInRange, OrderPeerEnum.KNOWLEDGE_ASCENDING)
        assertEquals(orderPeerEnumCorrect0, OrderPeerEnum.KNOWLEDGE_ASCENDING)
        assertEquals(orderPeerEnumCorrect1, OrderPeerEnum.KNOWLEDGE_DESCENDING)
    }

}