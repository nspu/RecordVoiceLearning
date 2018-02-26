package fr.nspu.dev.recordvoicelearning.view.fragment

import android.annotation.SuppressLint
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import fr.nspu.dev.recordvoicelearning.RecordVoiceLearning
import fr.nspu.dev.recordvoicelearning.R
import fr.nspu.dev.recordvoicelearning.controller.entity.FolderEntity
import fr.nspu.dev.recordvoicelearning.databinding.FragmentAddFolderBinding

class AddFolderFragment : Fragment() {

    private var mBinding: FragmentAddFolderBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_folder, container, false)

        setHasOptionsMenu(true)

        return mBinding!!.root
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.add_folder_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item!!.itemId

        when (id) {
            R.id.action_validate_add_folder -> {
                val folderEntity = FolderEntity()
                folderEntity.name = mBinding!!.addNameEt.text.toString()
                folderEntity.typeQuestion = mBinding!!.addTypeQuestionEt.text.toString()
                folderEntity.typeAnswer = mBinding!!.addTypeAnswerEt.text.toString()
                DatabaseAsync().execute(folderEntity)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class DatabaseAsync : AsyncTask<FolderEntity, Void, Unit>() {

        override fun doInBackground(vararg folderEntities: FolderEntity) {
            (activity!!.application as RecordVoiceLearning).database.folderDao().insertFolderSync(folderEntities[0])
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            Snackbar.make(this@AddFolderFragment.activity!!.findViewById(R.id.fragment_container), R.string.folder_add, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()

            this@AddFolderFragment.fragmentManager!!.popBackStack()
        }
    }

    companion object {
        val TAG = "AddFolderFragment"
    }

}
