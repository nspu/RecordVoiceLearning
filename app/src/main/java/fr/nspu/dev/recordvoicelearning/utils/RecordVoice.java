package fr.nspu.dev.recordvoicelearning.utils;

import android.content.Context;
import android.media.MediaRecorder;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import fr.nspu.dev.recordvoicelearning.view.dialog.RecordDialog;

/**
 * Created by nspu on 18-02-12.
 * <p>
 * Implement management for record sound
 */

public abstract class RecordVoice implements View.OnClickListener {
    final String LOG_TAG = "RECODE_VOICE";

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public final int MAX_VALUE_PROGRESSBAR = 5000;

    private final Button mButton;
    private final String mFileName;
        private MediaRecorder mRecorder = null;
    private boolean isRecord = false;
    private final String mDirectory;

    private final RecordDialog mRecordDialog;


    public RecordVoice(String mFileName, String directory,Button mButton , Context context) {
        this.mButton = mButton;
        if (mButton != null) {
            this.mButton.setOnClickListener(this);
        }
        this.mFileName = mFileName;
        mDirectory = directory;

        //if there is no context -> no interface
        if (context != null) {
            mRecordDialog = new RecordDialog(context);
            mRecordDialog.getSoundProgress().setMax(MAX_VALUE_PROGRESSBAR);
            mRecordDialog.getStopButton().setOnClickListener(v -> {
                //stop
                isRecord = false;
                RecordVoice.this.stopRecording();
                mRecordDialog.hide();
            });
        } else {
            mRecordDialog = null;
        }

    }

    public void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setOutputFile(mDirectory + "/" + mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed : Record: " + mFileName);
        }

        mRecorder.start();

        //if the dialog is null, doesn't display
        if(mRecordDialog != null){
            new Thread(() -> {
                isRecord = true;
                int max = 0;
                while (isRecord) {
                    int maxAmplitude = mRecorder.getMaxAmplitude();
                    if (max < maxAmplitude) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    max = defineMax(max, maxAmplitude);
                    if(mRecordDialog!=null){
                        mRecordDialog.getSoundProgress().setProgress(max);
                    }
                }
            }).start();

            diplayDialog();
        }
    }

    public int defineMax(int max, int maxAmplitude) {
        if (max < maxAmplitude) {
            if (maxAmplitude > MAX_VALUE_PROGRESSBAR) {
                max = MAX_VALUE_PROGRESSBAR;
            } else {
                max = maxAmplitude;
            }
        } else {
            if (max > 0) {
                max--;
            }
        }
        return max;
    }


    public void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        onRecordFinish(mFileName);
    }

    @Override
    public void onClick(View v) {
        if (mButton.getId() == v.getId()) {
            this.startRecording();
        }
    }

    private void diplayDialog() {
        mRecordDialog.setCancelable(false);
        mRecordDialog.show();
    }

    protected abstract void onRecordFinish(String fileName);
}
