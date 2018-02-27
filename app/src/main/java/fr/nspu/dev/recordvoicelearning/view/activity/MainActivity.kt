package fr.nspu.dev.recordvoicelearning.view.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import fr.nspu.dev.recordvoicelearning.R
import fr.nspu.dev.recordvoicelearning.model.Folder
import fr.nspu.dev.recordvoicelearning.view.fragment.AddFolderFragment
import fr.nspu.dev.recordvoicelearning.view.fragment.FolderFragment
import fr.nspu.dev.recordvoicelearning.view.fragment.FolderListFragment

/**
 * Created by nspu on 18-02-04.
 */

class MainActivity : AppCompatActivity() {
    private var permissionToRecordAccepted = false
    private val permissions = arrayOf(Manifest.permission.RECORD_AUDIO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar!!.setDisplayShowHomeEnabled(true)

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        // Add product list fragment if this is first creation
        if (savedInstanceState == null) {
            val folderListFragment = FolderListFragment()

            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, folderListFragment, FolderListFragment.TAG).commit()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_RECORD_AUDIO_PERMISSION -> permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
        if (!permissionToRecordAccepted) finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    /** Shows the folder detail fragment  */
    fun showFolder(folder: Folder) {

        val productFragment = FolderFragment.forFolder(folder.id!!, folder.questionToAnswer!!)

        supportFragmentManager
                .beginTransaction()
                .addToBackStack("folder")
                .replace(R.id.fragment_container,
                        productFragment, null).commit()
    }


    fun addFolder() {
        val addFolderFragment = AddFolderFragment()

        supportFragmentManager
                .beginTransaction()
                .addToBackStack("addfolder")
                .replace(R.id.fragment_container,
                        addFolderFragment, null).commit()
    }

    companion object {

        private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }
}
