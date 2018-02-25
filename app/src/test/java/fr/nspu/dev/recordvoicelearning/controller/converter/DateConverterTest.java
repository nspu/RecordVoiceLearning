package fr.nspu.dev.recordvoicelearning.controller.converter;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by nspu on 18-02-22.
 */
public class DateConverterTest {
    @Test
    public void toDate() throws Exception {
        Date now  = new Date();
        Long nowTimestamp = now.getTime();

        Date dateNull = DateConverter.Companion.toDate(null);
        Date dateEqual = DateConverter.Companion.toDate(nowTimestamp);

        assertNull(dateNull);
        assertEquals(dateEqual, now);
    }

    @Test
    public void toTimestamp() throws Exception {
        Date now  = new Date();
        Long nowTimestamp = now.getTime();

        Long timestampNull = DateConverter.Companion.toTimestamp(null);
        Long timestampEqual = DateConverter.Companion.toTimestamp(now);

        assertNull(timestampNull);
        assertEquals(timestampEqual, nowTimestamp);
    }

}