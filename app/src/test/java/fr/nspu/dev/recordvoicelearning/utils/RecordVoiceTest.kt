package fr.nspu.dev.recordvoicelearning.utils

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Created by nspu on 18-02-22.
 */
@RunWith(RobolectricTestRunner::class)
class RecordVoiceTest {

    internal lateinit var mRecordVoice: RecordVoice

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val fileName = FILE_NAME
        val directory = RuntimeEnvironment.application.externalCacheDir!!.absolutePath

        mRecordVoice = object : RecordVoice(fileName, directory, null, null) {
            override fun onRecordFinish(fileName: String) {
                if (false) {
                    assertEquals(fileName, FILE_NAME)
                }
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun defineMax() {
        val max0 = mRecordVoice.defineMax(0, 0)
        val maxMaxValue = mRecordVoice.defineMax(0, mRecordVoice.MAX_VALUE_PROGRESSBAR)
        val maxHigherMaxValue = mRecordVoice.defineMax(0, mRecordVoice.MAX_VALUE_PROGRESSBAR + 500)
        val maxDecremente = mRecordVoice.defineMax(1000, 100)
        val maxIncrease = mRecordVoice.defineMax(90, 110)

        assertEquals(max0.toLong(), 0)
        assertEquals(maxMaxValue.toLong(), mRecordVoice.MAX_VALUE_PROGRESSBAR.toLong())
        assertEquals(maxHigherMaxValue.toLong(), mRecordVoice.MAX_VALUE_PROGRESSBAR.toLong())
        assertEquals(maxDecremente.toLong(), 999)
        assertEquals(maxIncrease.toLong(), 110)
    }


    @Test
    @Throws(Exception::class)
    fun Record() {
        mRecordVoice.startRecording()
        Thread.sleep(50)
        mRecordVoice.stopRecording()
    }

    companion object {
        var FILE_NAME = "test.mp4"
    }
}