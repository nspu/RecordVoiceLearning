package fr.nspu.dev.recordvoicelearning.controller.converter

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.util.*

/**
 * Created by nspu on 18-02-22.
 */
class DateConverterTest {

    val dateConverter = DateConverter()

    @Test
    @Throws(Exception::class)
    fun toDate() {
        val now = Date()
        val nowTimestamp = now.time


        val dateNull = dateConverter.toDate(null)
        val dateEqual = dateConverter.toDate(nowTimestamp)

        assertNull(dateNull)
        assertEquals(dateEqual, now)
    }

    @Test
    @Throws(Exception::class)
    fun toTimestamp() {
        val now = Date()
        val nowTimestamp = now.time

        val timestampNull = dateConverter.toTimestamp(null)
        val timestampEqual = dateConverter.toTimestamp(now)

        assertNull(timestampNull)
        assertEquals(timestampEqual, nowTimestamp)
    }

}