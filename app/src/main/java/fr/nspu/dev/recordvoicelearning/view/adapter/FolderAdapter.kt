package fr.nspu.dev.recordvoicelearning.view.adapter

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import java.util.Objects


import fr.nspu.dev.recordvoicelearning.R
import fr.nspu.dev.recordvoicelearning.databinding.ItemFolderBinding
import fr.nspu.dev.recordvoicelearning.model.Folder
import fr.nspu.dev.recordvoicelearning.utils.callback.ClickCallback

/**
 * Created by nspu on 18-02-02.
 */

class FolderAdapter(private val mFolderClickCallback: ClickCallback?) : RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {

    private var mFolderList: List<Folder>? = null

    fun setFolderList(folderList: List<Folder>) {
        if (mFolderList == null) {
            mFolderList = folderList
            notifyItemRangeInserted(0, folderList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return mFolderList!!.size
                }

                override fun getNewListSize(): Int {
                    return folderList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return mFolderList!![oldItemPosition].id === folderList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newFolder = folderList[newItemPosition]
                    val oldFolder = mFolderList!![oldItemPosition]
                    return (newFolder.id === oldFolder.id
                            && newFolder.name == oldFolder.name
                            && newFolder.typeAnswer == oldFolder.typeAnswer
                            && newFolder.typeQuestion == oldFolder.typeQuestion)
                    //                            && newFolder.getCreatedAt().getTime() == oldFolder.getCreatedAt().getTime()
                    //                            && newFolder.getUpdatedAt().getTime() == oldFolder.getCreatedAt().getTime();
                }
            })
            mFolderList = folderList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val binding = DataBindingUtil
                .inflate<ItemFolderBinding>(LayoutInflater.from(parent.context),
                        R.layout.item_folder,
                        parent,
                        false)
        binding.callback = mFolderClickCallback
        return FolderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        holder.binding.folder = mFolderList!![position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (mFolderList == null) 0 else mFolderList!!.size
    }

    class FolderViewHolder(val binding: ItemFolderBinding) : RecyclerView.ViewHolder(binding.root)
}
