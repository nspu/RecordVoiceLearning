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
        OrderPeerEnum orderPeerEnumNotInRange = OrderPeerEnumConverter.INSTANCE.toOrderPeerEnum(5);
        OrderPeerEnum orderPeerEnumCorrect0 = OrderPeerEnumConverter.INSTANCE.toOrderPeerEnum(0);
        OrderPeerEnum orderPeerEnumCorrect1 = OrderPeerEnumConverter.INSTANCE.toOrderPeerEnum(1);

        assertEquals(orderPeerEnumNotInRange, OrderPeerEnum.KNOWLEDGE_ASCENDING);
        assertEquals(orderPeerEnumCorrect0, OrderPeerEnum.KNOWLEDGE_ASCENDING);
        assertEquals(orderPeerEnumCorrect1, OrderPeerEnum.KNOWLEDGE_DESCENDING);
    }

    @Test
    public void toInt() throws Exception {
        int i0 = OrderPeerEnumConverter.INSTANCE.toInt(OrderPeerEnum.KNOWLEDGE_ASCENDING);
        int i1 = OrderPeerEnumConverter.INSTANCE.toInt(OrderPeerEnum.KNOWLEDGE_DESCENDING);
        int iDefault = OrderPeerEnumConverter.INSTANCE.toInt(null);

        assertEquals(i0, 0);
        assertEquals(i1, 1);
        assertEquals(iDefault, 0);
    }

}