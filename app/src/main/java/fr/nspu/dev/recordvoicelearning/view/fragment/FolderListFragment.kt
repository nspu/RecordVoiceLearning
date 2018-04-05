package fr.nspu.dev.recordvoicelearning.view.fragment

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v4.app.Fragment
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
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
import fr.nspu.dev.recordvoicelearning.databinding.FragmentListFolderBinding
import fr.nspu.dev.recordvoicelearning.utils.callback.ClickCallback
import fr.nspu.dev.recordvoicelearning.view.activity.SettingsActivity
import fr.nspu.dev.recordvoicelearning.view.activity.MainActivity
import fr.nspu.dev.recordvoicelearning.view.adapter.FolderAdapter
import fr.nspu.dev.recordvoicelearning.viewmodel.FolderListViewModel

class FolderListFragment : Fragment() {

    private var mFolderAdapter: FolderAdapter? = null

        private lateinit var mBinding: FragmentListFolderBinding

    private val mFolderClickCallback = object : ClickCallback {
        override fun onClick(o: Any) {
            val folder = o as FolderEntity
            (activity as MainActivity).showFolder(folder)
        }

        override fun onLongClick(o: Any): Boolean {
            val folder = o as FolderEntity
            DatabaseAsync().execute(folder)
            return true
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_folder, container, false)

        mFolderAdapter = FolderAdapter(mFolderClickCallback)
        mBinding.foldersList.adapter = mFolderAdapter
        setHasOptionsMenu(true)

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val viewModel = ViewModelProviders
                .of(this)
                .get(FolderListViewModel::class.java)

        subscribeUi(viewModel)

        val fab = view!!.findViewById<FloatingActionButton>(R.id.fab_add_folder)
        fab.setOnClickListener { _ -> (activity as MainActivity).addFolder() }

        super.onActivityCreated(savedInstanceState)
    }

    private fun subscribeUi(viewModel: FolderListViewModel) {
        // Update the list when the data changes
        viewModel.folders.observe(this, Observer { folderEntities ->
            if (folderEntities != null) {
                mBinding.isLoading = false
                mFolderAdapter!!.setFolderList(folderEntities)
            } else {
                mBinding.isLoading = true
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.folder_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId

        when (id) {
            R.id.settings -> {
                val intent = Intent(activity!!.applicationContext, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    @SuppressLint("StaticFieldLeak")
    private inner class DatabaseAsync : AsyncTask<FolderEntity, Void, Unit>() {


        override fun doInBackground(vararg folderEntities: FolderEntity) {
            (activity!!.application as RecordVoiceLearning).database!!.folderDao().deleteFolders(folderEntities[0])
        }


        override fun onPostExecute(result: Unit) {
            Snackbar.make(this@FolderListFragment.activity!!.findViewById(R.id.fragment_container), R.string.folder_deleted, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
            super.onPostExecute(result)
        }
    }

    companion object {

        val TAG = "FolderListViewModel"
    }
}
