package fr.nspu.dev.recordvoicelearning.controller.converter

import org.junit.Test

import java.util.Date

import org.junit.Assert.*

/**
 * Created by nspu on 18-02-22.
 */
class DateConverterTest {
    @Test
    @Throws(Exception::class)
    fun toDate() {
        val now = Date()
        val nowTimestamp = now.time

        val dateNull = DateConverter.toDate(null)
        val dateEqual = DateConverter.toDate(nowTimestamp)

        assertNull(dateNull)
        assertEquals(dateEqual, now)
    }

    @Test
    @Throws(Exception::class)
    fun toTimestamp() {
        val now = Date()
        val nowTimestamp = now.time

        val timestampNull = DateConverter.toTimestamp(null)
        val timestampEqual = DateConverter.toTimestamp(now)

        assertNull(timestampNull)
        assertEquals(timestampEqual, nowTimestamp)
    }

}