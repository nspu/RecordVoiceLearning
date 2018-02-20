package fr.nspu.dev.recordvoicelearning.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import fr.nspu.dev.recordvoicelearning.R;
import fr.nspu.dev.recordvoicelearning.databinding.ItemPeerBinding;
import fr.nspu.dev.recordvoicelearning.model.Peer;
import fr.nspu.dev.recordvoicelearning.utils.callback.ClickCallback;

/**
 * Created by nspu on 18-02-06.
 */

public class PeerAdapter extends RecyclerView.Adapter<PeerAdapter.PeerViewHolder> {

    private List<? extends Peer> mPeerList;
    private final Context mContext;

    @Nullable
    private final ClickCallback mPeerClickCallback;

    public PeerAdapter(@Nullable ClickCallback clickCallback, Context context) {
        mPeerClickCallback = clickCallback;
        mContext = context;
    }

    @Override
    public PeerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPeerBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_peer,
                parent,
                false);
        binding.setCallback(mPeerClickCallback);
        return new PeerViewHolder(binding);
    }

    public void setPeerList(final List<? extends Peer> peerList) {
        if (mPeerList == null) {
            mPeerList = peerList;
            notifyItemRangeInserted(0, peerList.size());

        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mPeerList.size();
                }

                @Override
                public int getNewListSize() {
                    return peerList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mPeerList.get(oldItemPosition).getId() ==
                            peerList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Peer newPeer = peerList.get(newItemPosition);
                    Peer oldPeer = mPeerList.get(oldItemPosition);
                    return newPeer.getId() == oldPeer.getId()
                            && newPeer.getFolderId() == oldPeer.getFolderId()
                            && Objects.equals(newPeer.getFileNameAnswer(),oldPeer.getFileNameAnswer())
                            && Objects.equals(newPeer.getFileNameQuestion(),oldPeer.getFileNameQuestion());
//                            && newPeer.getCreatedAt().getTime() == oldPeer.getCreatedAt().getTime()
//                            && newPeer.getUpdatedAt().getTime() == oldPeer.getUpdatedAt().getTime();
                }
            });
            mPeerList = peerList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public void onBindViewHolder(PeerViewHolder holder, int position) {
        String peerName = mContext.getString(R.string.pair_colon_id, mPeerList.get(position).getId());
        holder.binding.nameTv.setText(peerName);
        holder.binding.setPeer(mPeerList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mPeerList == null ? 0 : mPeerList.size();
    }

    static class PeerViewHolder extends RecyclerView.ViewHolder {

        final ItemPeerBinding binding;
        public PeerViewHolder(ItemPeerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
