package fr.nspu.dev.recordvoicelearning.view.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import java.util.Objects

import fr.nspu.dev.recordvoicelearning.R
import fr.nspu.dev.recordvoicelearning.databinding.ItemPeerBinding
import fr.nspu.dev.recordvoicelearning.model.Peer
import fr.nspu.dev.recordvoicelearning.utils.callback.ClickCallback

/**
 * Created by nspu on 18-02-06.
 */

class PeerAdapter(private val mPeerClickCallback: ClickCallback?, private val mContext: Context) : RecyclerView.Adapter<PeerAdapter.PeerViewHolder>() {

    private var mPeerList: List<Peer>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeerViewHolder {
        val binding = DataBindingUtil.inflate<ItemPeerBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_peer,
                parent,
                false)
        binding.callback = mPeerClickCallback
        return PeerViewHolder(binding)
    }

    fun setPeerList(peerList: List<Peer>) {
        if (mPeerList == null) {
            mPeerList = peerList
            notifyItemRangeInserted(0, peerList.size)

        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return mPeerList!!.size
                }

                override fun getNewListSize(): Int {
                    return peerList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return mPeerList!![oldItemPosition].id === peerList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newPeer = peerList[newItemPosition]
                    val oldPeer = mPeerList!![oldItemPosition]
                    return (newPeer.id === oldPeer.id
                            && newPeer.folderId === oldPeer.folderId
                            && newPeer.fileNameAnswer == oldPeer.fileNameAnswer
                            && newPeer.fileNameQuestion == oldPeer.fileNameQuestion
                            && newPeer.knowledge == oldPeer.knowledge
                            && newPeer.count == oldPeer.count)
                }
            })
            mPeerList = peerList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onBindViewHolder(holder: PeerViewHolder, position: Int) {
        val peerName = mContext.getString(R.string.pair_colon_id, mPeerList!![position].id)
        holder.binding.nameTv.text = peerName
        holder.binding.peer = mPeerList!![position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (mPeerList == null) 0 else mPeerList!!.size
    }

    class PeerViewHolder(val binding: ItemPeerBinding) : RecyclerView.ViewHolder(binding.root)
}
