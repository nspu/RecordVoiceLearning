package fr.nspu.dev.recordvoicelearning.utils

import android.media.MediaPlayer
import android.util.Log
import android.view.View
import android.widget.Button

import java.io.IOException

/**
 * Created by nspu on 18-02-12.
 *
 * Implement management for listen sound
 */

class ListenVoice(private val mButton: Button, private val mFileName: String, private val mDirectory: String) : View.OnClickListener {
    internal val LOG_TAG = "LISTEN_VOICE"

    private var mPlayer: MediaPlayer? = null
    private var isPlaying = false

    init {
        this.mButton.setOnClickListener(this)
    }

    fun startPlaying() {
        mPlayer = MediaPlayer()
        try {
            val file = mDirectory + "/" + mFileName
            mPlayer!!.setDataSource(file)
            mPlayer!!.prepare()
            mPlayer!!.start()
        } catch (e: IOException) {
            Log.e(LOG_TAG, "prepare() failed + " + mFileName + e.message)
        }

    }

    fun stopPlaying() {
        mPlayer!!.release()
        mPlayer = null
    }

    override fun onClick(v: View) {
        if (mButton.id == v.id) {
            if (this.isPlaying) {
                this.isPlaying = false
                this.stopPlaying()
            } else {
                this.isPlaying = true
                this.startPlaying()
            }
        }
    }
}
