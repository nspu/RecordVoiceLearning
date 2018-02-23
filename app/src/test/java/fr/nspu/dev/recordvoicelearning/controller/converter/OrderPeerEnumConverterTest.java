package fr.nspu.dev.recordvoicelearning.controller.converter;

import org.junit.Test;

import fr.nspu.dev.recordvoicelearning.utils.OrderPeerEnum;

import static org.junit.Assert.*;

/**
 * Created by nspu on 18-02-22.
 */
public class OrderPeerEnumConverterTest {
    @Test
    public void toOrderPeerEnum() throws Exception {
        OrderPeerEnum orderPeerEnumNotInRange = OrderPeerEnumConverter.toOrderPeerEnum(5);
        OrderPeerEnum orderPeerEnumCorrect0 = OrderPeerEnumConverter.toOrderPeerEnum(0);
        OrderPeerEnum orderPeerEnumCorrect1 = OrderPeerEnumConverter.toOrderPeerEnum(1);

        assertEquals(orderPeerEnumNotInRange, OrderPeerEnum.DEFAULT);
        assertEquals(orderPeerEnumCorrect0, OrderPeerEnum.KNOWLEDGE_ASCENDING);
        assertEquals(orderPeerEnumCorrect1, OrderPeerEnum.KNOWLEDGE_DESCENDING);
    }

    @Test
    public void toInt() throws Exception {
        int i0 = OrderPeerEnumConverter.toInt(OrderPeerEnum.KNOWLEDGE_ASCENDING);
        int i1 = OrderPeerEnumConverter.toInt(OrderPeerEnum.KNOWLEDGE_DESCENDING);
        int iDefault = OrderPeerEnumConverter.toInt(null);

        assertEquals(i0, 0);
        assertEquals(i1, 1);
        assertEquals(iDefault, 0);
    }

}