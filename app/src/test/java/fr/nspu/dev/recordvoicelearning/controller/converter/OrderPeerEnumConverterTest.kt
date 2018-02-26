package fr.nspu.dev.recordvoicelearning.controller.converter

import org.junit.Test

import fr.nspu.dev.recordvoicelearning.utils.OrderPeerEnum

import org.junit.Assert.*

/**
 * Created by nspu on 18-02-22.
 */
class OrderPeerEnumConverterTest {

    val orderPeerEnumConverter = OrderPeerEnumConverter()

    @Test
    @Throws(Exception::class)
    fun toOrderPeerEnum() {
        val orderPeerEnumNotInRange = orderPeerEnumConverter.toOrderPeerEnum(5)
        val orderPeerEnumCorrect0 = orderPeerEnumConverter.toOrderPeerEnum(0)
        val orderPeerEnumCorrect1 = orderPeerEnumConverter.toOrderPeerEnum(1)

        assertEquals(orderPeerEnumNotInRange, OrderPeerEnum.KNOWLEDGE_ASCENDING)
        assertEquals(orderPeerEnumCorrect0, OrderPeerEnum.KNOWLEDGE_ASCENDING)
        assertEquals(orderPeerEnumCorrect1, OrderPeerEnum.KNOWLEDGE_DESCENDING)
    }

    @Test
    @Throws(Exception::class)
    fun toInt() {
        val i0 = orderPeerEnumConverter.toInt(OrderPeerEnum.KNOWLEDGE_ASCENDING)!!
        val i1 = orderPeerEnumConverter.toInt(OrderPeerEnum.KNOWLEDGE_DESCENDING)!!

        assertEquals(i0.toLong(), 0)
        assertEquals(i1.toLong(), 1)
    }

}