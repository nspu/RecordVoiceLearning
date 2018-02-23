package fr.nspu.dev.recordvoicelearning.utils;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by nspu on 18-02-12.
 *
 * Implement management for listen sound
 */

public class ListenVoice implements View.OnClickListener{
    final String LOG_TAG = "LISTEN_VOICE";

    private MediaPlayer   mPlayer = null;
    private boolean isPlaying = false;

    private final Button mButton;
    private final String mFileName;

    private final String mDirectory;

    public ListenVoice(Button mButton, String mFileName,String directory) {
        this.mButton = mButton;
        this.mButton.setOnClickListener(this);
        this.mFileName = mFileName;
        mDirectory = directory;
    }

    public void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            String file = mDirectory + "/" + mFileName;
            mPlayer.setDataSource(file);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed + " + mFileName + e.getMessage());
        }
    }

    public void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    @Override
    public void onClick(View v) {
        if(mButton.getId() == v.getId()){
            if(this.isPlaying){
                this.isPlaying = false;
                this.stopPlaying();
            }else{
                this.isPlaying = true;
                this.startPlaying();
            }
        }
    }
}
