package fr.nspu.dev.recordvoicelearning.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nspu on 18-02-22.
 */
public class OrderPeerEnumTest {
    @Test
    public void toInt() throws Exception {
        int i0 = OrderPeerEnum.KNOWLEDGE_ASCENDING.toInt();
        int i1 = OrderPeerEnum.KNOWLEDGE_DESCENDING.toInt();

        assertEquals(i0, 0);
        assertEquals(i1, 1);
    }

    @Test
    public void toOrderPeerEnum() throws Exception {
        OrderPeerEnum orderPeerEnumNotInRange = OrderPeerEnum.toOrderPeerEnum(5);
        OrderPeerEnum orderPeerEnumCorrect0 = OrderPeerEnum.toOrderPeerEnum(0);
        OrderPeerEnum orderPeerEnumCorrect1 = OrderPeerEnum.toOrderPeerEnum(1);

        assertEquals(orderPeerEnumNotInRange, OrderPeerEnum.KNOWLEDGE_ASCENDING);
        assertEquals(orderPeerEnumCorrect0, OrderPeerEnum.KNOWLEDGE_ASCENDING);
        assertEquals(orderPeerEnumCorrect1, OrderPeerEnum.KNOWLEDGE_DESCENDING);
    }

}