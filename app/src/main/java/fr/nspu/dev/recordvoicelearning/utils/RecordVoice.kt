package fr.nspu.dev.recordvoicelearning.utils

import android.content.Context
import android.media.MediaRecorder
import android.support.annotation.VisibleForTesting
import android.util.Log
import android.view.View
import android.widget.Button

import java.io.IOException

import fr.nspu.dev.recordvoicelearning.view.dialog.RecordDialog

/**
 * Created by nspu on 18-02-12.
 *
 *
 * Implement management for record sound
 */

abstract class RecordVoice(private val mFileName: String, private val mDirectory: String, private val mButton: Button?, context: Context?) : View.OnClickListener {
    internal val LOG_TAG = "RECODE_VOICE"

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val MAX_VALUE_PROGRESSBAR = 5000
    private var mRecorder: MediaRecorder? = null
    private var isRecord = false

    private val mRecordDialog: RecordDialog?


    init {
        if (mButton != null) {
            this.mButton.setOnClickListener(this)
        }

        //if there is no context -> no interface
        if (context != null) {
            mRecordDialog = RecordDialog(context)
            mRecordDialog.soundProgress.max = MAX_VALUE_PROGRESSBAR
            mRecordDialog.stopButton.setOnClickListener { v ->
                //stop
                isRecord = false
                this@RecordVoice.stopRecording()
                mRecordDialog.dismiss()
            }
        } else {
            mRecordDialog = null
        }
    }

    fun startRecording() {
        mRecorder = MediaRecorder()
        mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mRecorder!!.setOutputFile(mDirectory + "/" + mFileName)
        mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        try {
            mRecorder!!.prepare()
        } catch (e: IOException) {
            Log.e(LOG_TAG, "prepare() failed : Record: " + mFileName)
        }

        mRecorder!!.start()

        //if the dialog is null, doesn't display
        if (mRecordDialog != null) {
            Thread {
                isRecord = true
                var max = 0
                while (isRecord) {
                    val maxAmplitude = mRecorder!!.maxAmplitude
                    if (max < maxAmplitude) {
                        try {
                            Thread.sleep(100)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }

                    }
                    max = defineMax(max, maxAmplitude)
                    mRecordDialog!!.soundProgress.progress = max
                }
            }.start()

            diplayDialog()
        }
    }

    fun defineMax(max: Int, maxAmplitude: Int): Int {
        var max = max
        if (max < maxAmplitude) {
            if (maxAmplitude!! > MAX_VALUE_PROGRESSBAR) {
                max = MAX_VALUE_PROGRESSBAR
            } else {
                max = maxAmplitude
            }
        } else {
            if (max > 0) {
                max--
            }
        }
        return max
    }


    fun stopRecording() {
        mRecorder!!.stop()
        mRecorder!!.release()
        mRecorder = null
        onRecordFinish(mFileName)
    }

    override fun onClick(v: View) {
        if (mButton!!.id == v.id) {
            this.startRecording()
        }
    }

    private fun diplayDialog() {
        mRecordDialog!!.setCancelable(false)
        mRecordDialog.show()
    }

    protected abstract fun onRecordFinish(fileName: String)
}
