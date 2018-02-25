package fr.nspu.dev.recordvoicelearning.controller.converter;

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

        Date dateNull = DateConverter.INSTANCE.toDate(null);
        Date dateEqual = DateConverter.INSTANCE.toDate(nowTimestamp);

        assertNull(dateNull);
        assertEquals(dateEqual, now);
    }

    @Test
    public void toTimestamp() throws Exception {
        Date now  = new Date();
        Long nowTimestamp = now.getTime();

        Long timestampNull = DateConverter.INSTANCE.toTimestamp(null);
        Long timestampEqual = DateConverter.INSTANCE.toTimestamp(now);

        assertNull(timestampNull);
        assertEquals(timestampEqual, nowTimestamp);
    }

}