package fr.nspu.dev.recordvoicelearning.view;


import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.UUID;

import fr.nspu.dev.recordvoicelearning.RecordVoiceLearning;
import fr.nspu.dev.recordvoicelearning.R;
import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity;
import fr.nspu.dev.recordvoicelearning.databinding.ActivityRecordBinding;
import fr.nspu.dev.recordvoicelearning.model.Folder;
import fr.nspu.dev.recordvoicelearning.utils.ListenVoice;
import fr.nspu.dev.recordvoicelearning.utils.RecordVoice;

/**
 * Created by nspu on 18-02-04.
 */

public class RecordActivity extends AppCompatActivity {

    public static final String KEY_FOLDER_ID = "id_folder";
    final String TAG = "RecordActivity";
    private Folder mFolder;

    private PeerEntity mPeer;

    private ActivityRecordBinding mBinding;

    private boolean mExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        int folderId = getIntent().getIntExtra(KEY_FOLDER_ID, -1);

        if (folderId == -1) {
            //TODO error
            finish();
        }

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);

        new LoadFolderTask().execute(folderId);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_record);
        mBinding.exitBtn.setOnClickListener((View v) -> exit());

        mBinding.recordAnotherBtn.setOnClickListener((View v) -> recordAnAnother());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!mPeer.getFileNameAnswer().isEmpty() || !mPeer.getFileNameQuestion().isEmpty()) {
            Snackbar.make(mBinding.llRecord, "Voulez-vous vraiment quitter?", Snackbar.LENGTH_LONG)
                    .setAction("Quitter", v -> super.onBackPressed()).show();
        } else {
            super.onBackPressed();
        }
    }

    private void init() {
        setTitle(mFolder.getName());

        String uuidGenerate = UUID.randomUUID().toString();

        String fileNameQuestion = mFolder.getId() + "_q_" + uuidGenerate + ".mp4";
        String fileNameAnswer = mFolder.getId() + "_a_" + uuidGenerate + ".mp4";
        String directory = getExternalCacheDir().getAbsolutePath();

        mBinding.resetBtn.setOnClickListener(v -> init());

        new RecordVoice(fileNameQuestion, directory, mBinding.recordQuestionBtn, this) {

            @Override
            public void onRecordFinish(String fileName) {
                mPeer.setFileNameQuestion(fileName);
                mBinding.setPeer(mPeer);
            }
        };

        new RecordVoice(fileNameAnswer, directory, mBinding.recordAnswerBtn, this) {
            @Override
            public void onRecordFinish(String fileName) {
                mPeer.setFileNameAnswer(fileName);
                mBinding.setPeer(mPeer);
            }
        };

        new ListenVoice(mBinding.listenQuestionBtn, fileNameQuestion, directory);

        new ListenVoice(mBinding.listenAnswerBtn, fileNameAnswer, directory);

        mPeer = new PeerEntity();
        mPeer.setFolderId(mFolder.getId());
        mBinding.setPeer(mPeer);
    }


    private void exit() {
        mExit = true;
        register();
    }

    private void recordAnAnother() {
        mExit = false;
        register();

    }

    private void register() {
        try {
            moveCacheToFiles(mPeer.getFileNameQuestion());
        } catch (Exception e) {
            //Log erreur
            String m = "Erreur: " + mPeer.getFileNameQuestion() + " " + e.getMessage();
            Snackbar.make(mBinding.llRecord, m, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        }

        try {
            moveCacheToFiles(mPeer.getFileNameAnswer());
        } catch (Exception e) {
            //Log erreur
            String m = "Erreur: " + mPeer.getFileNameAnswer() + " " + e.getMessage();
            Snackbar.make(mBinding.llRecord, m, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        }

        new SavePeerTask().execute(mPeer);

    }

    private void moveCacheToFiles(String fileName) throws SecurityException, NullPointerException {
        String directoryCache = getExternalCacheDir().getAbsolutePath();
        String directoryFinal = getExternalFilesDir(null).getAbsolutePath();
        File fromQuestion = new File(directoryCache + "/" + fileName);
        File toQuestion = new File(directoryFinal + "/" + fileName);
        if (!fromQuestion.renameTo(toQuestion)) {
            Log.d(TAG, "error move file");
        }
    }


    private class LoadFolderTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            mFolder = ((RecordVoiceLearning) getApplication()).getDatabase()
                    .folderDao()
                    .loadFolderByIdSync(integers[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            init();
        }
    }

    private class SavePeerTask extends AsyncTask<PeerEntity, Void, Void> {


        @Override
        protected Void doInBackground(PeerEntity... peers) {
            ((RecordVoiceLearning) getApplication()).getDatabase()
                    .peerDao()
                    .insertPeerSync(peers[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mExit) {
                RecordActivity.this.finish();
            } else {
                Toast.makeText(RecordActivity.this, "Paire enregistrer!", Toast.LENGTH_SHORT)
                        .show();
                init();
            }
        }
    }

}
