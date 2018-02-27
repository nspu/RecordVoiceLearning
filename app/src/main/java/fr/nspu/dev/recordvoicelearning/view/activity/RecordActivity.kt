package fr.nspu.dev.recordvoicelearning.view.activity


import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import java.io.File
import java.util.UUID

import fr.nspu.dev.recordvoicelearning.RecordVoiceLearning
import fr.nspu.dev.recordvoicelearning.R
import fr.nspu.dev.recordvoicelearning.controller.entity.PeerEntity
import fr.nspu.dev.recordvoicelearning.databinding.ActivityRecordBinding
import fr.nspu.dev.recordvoicelearning.model.Folder
import fr.nspu.dev.recordvoicelearning.utils.ListenVoice
import fr.nspu.dev.recordvoicelearning.utils.RecordVoice

/**
 * Created by nspu on 18-02-04.
 */

class RecordActivity : AppCompatActivity() {
    internal val TAG = "RecordActivity"
    private var mFolder: Folder? = null

    private var mPeer: PeerEntity? = null

    private var mBinding: ActivityRecordBinding? = null

    private var mExit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        val folderId = intent.getIntExtra(KEY_FOLDER_ID, -1)

        if (folderId == -1) {
            //TODO error
            finish()
        }

        this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar!!.setDisplayShowHomeEnabled(true)

        LoadFolderTask().execute(folderId)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_record)
        mBinding!!.exitBtn.setOnClickListener { _: View -> exit() }

        mBinding!!.recordAnotherBtn.setOnClickListener { _: View -> recordAnAnother() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (android.R.id.home == item.itemId) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (mPeer!!.fileNameAnswer != null || mPeer!!.fileNameQuestion != null) {
            Snackbar.make(mBinding!!.llRecord, "Voulez-vous vraiment quitter?", Snackbar.LENGTH_LONG)
                    .setAction("Quitter") { v -> super.onBackPressed() }.show()
        } else {
            super.onBackPressed()
        }
    }

    private fun init() {
        title = mFolder!!.name

        val uuidGenerate = UUID.randomUUID().toString()

        val fileNameQuestion = mFolder!!.id.toString() + "_q_" + uuidGenerate + ".mp4"
        val fileNameAnswer = mFolder!!.id.toString() + "_a_" + uuidGenerate + ".mp4"
        val directory = externalCacheDir!!.absolutePath

        mBinding!!.resetBtn.setOnClickListener { _: View? -> init() }

        object : RecordVoice(fileNameQuestion, directory, mBinding!!.recordQuestionBtn, this) {

            public override fun onRecordFinish(fileName: String) {
                mPeer!!.fileNameQuestion = fileName
                mBinding!!.peer = mPeer
            }
        }

        object : RecordVoice(fileNameAnswer, directory, mBinding!!.recordAnswerBtn, this) {
            public override fun onRecordFinish(fileName: String) {
                mPeer!!.fileNameAnswer = fileName
                mBinding!!.peer = mPeer
            }
        }

        ListenVoice(mBinding!!.listenQuestionBtn, fileNameQuestion, directory)

        ListenVoice(mBinding!!.listenAnswerBtn, fileNameAnswer, directory)

        mPeer = PeerEntity(mFolder!!.id)
        mBinding!!.peer = mPeer
    }


    private fun exit() {
        mExit = true
        register()
    }

    private fun recordAnAnother() {
        mExit = false
        register()

    }

    private fun register() {
        try {
            moveCacheToFiles(mPeer!!.fileNameQuestion)
        } catch (e: Exception) {
            //Log erreur
            val m = "Erreur: " + mPeer!!.fileNameQuestion + " " + e.message
            Snackbar.make(mBinding!!.llRecord, m, Snackbar.LENGTH_SHORT).setAction("Action", null).show()
        }

        try {
            moveCacheToFiles(mPeer!!.fileNameAnswer)
        } catch (e: Exception) {
            //Log erreur
            val m = "Erreur: " + mPeer!!.fileNameAnswer + " " + e.message
            Snackbar.make(mBinding!!.llRecord, m, Snackbar.LENGTH_SHORT).setAction("Action", null).show()
        }

        SavePeerTask().execute(mPeer)

    }

    @Throws(SecurityException::class, NullPointerException::class)
    private fun moveCacheToFiles(fileName: String?) {
        val directoryCache = externalCacheDir!!.absolutePath
        val directoryFinal = getExternalFilesDir(null)!!.absolutePath
        val fromQuestion = File(directoryCache + "/" + fileName)
        val toQuestion = File(directoryFinal + "/" + fileName)
        if (!fromQuestion.renameTo(toQuestion)) {
            Log.d(TAG, "error move file")
        }
    }


    @SuppressLint("StaticFieldLeak")
    private inner class LoadFolderTask : AsyncTask<Int, Void, Unit>() {

        override fun doInBackground(vararg integers: Int?) {
            mFolder = (application as RecordVoiceLearning).database!!
                    .folderDao()
                    .loadFolderByIdSync(integers[0]!!)
        }

        override fun onPostExecute(result: Unit) {
            super.onPostExecute(result)
            init()
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class SavePeerTask : AsyncTask<PeerEntity, Void, Unit>() {


        override fun doInBackground(vararg peers: PeerEntity) {
            (application as RecordVoiceLearning).database!!
                    .peerDao()
                    .insertPeerSync(peers[0])
        }

        override fun onPostExecute(result: Unit) {
            super.onPostExecute(result)
            if (mExit) {
                this@RecordActivity.finish()
            } else {
                Toast.makeText(this@RecordActivity, "Paire enregistrer!", Toast.LENGTH_SHORT)
                        .show()
                init()
            }
        }
    }

    companion object {

        val KEY_FOLDER_ID = "id_folder"
    }

}
