package fr.nspu.dev.recordvoicelearning.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;


import fr.nspu.dev.recordvoicelearning.R;
import fr.nspu.dev.recordvoicelearning.databinding.ItemFolderBinding;
import fr.nspu.dev.recordvoicelearning.model.Folder;
import fr.nspu.dev.recordvoicelearning.utils.callback.ClickCallback;

/**
 * Created by nspu on 18-02-02.
 */

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {

    private List<? extends Folder> mFolderList;

    @Nullable
    private final ClickCallback mFolderClickCallback;

    public FolderAdapter(@Nullable ClickCallback clickCallback) {
        mFolderClickCallback = clickCallback;
    }

    public void setFolderList(final List<? extends Folder> folderList) {
        if (mFolderList == null) {
            mFolderList = folderList;
            notifyItemRangeInserted(0, folderList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mFolderList.size();
                }

                @Override
                public int getNewListSize() {
                    return folderList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mFolderList.get(oldItemPosition).getId() ==
                            folderList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Folder newFolder = folderList.get(newItemPosition);
                    Folder oldFolder = mFolderList.get(oldItemPosition);
                    return newFolder.getId() == oldFolder.getId()
                            && Objects.equals(newFolder.getName(),oldFolder.getName())
                            && Objects.equals(newFolder.getTypeAnswer(),oldFolder.getTypeAnswer())
                            && Objects.equals(newFolder.getTypeQuestion(),oldFolder.getTypeQuestion());
//                            && newFolder.getCreatedAt().getTime() == oldFolder.getCreatedAt().getTime()
//                            && newFolder.getUpdatedAt().getTime() == oldFolder.getCreatedAt().getTime();
                }
            });
            mFolderList = folderList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFolderBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_folder,
                        parent,
                        false);
        binding.setCallback(mFolderClickCallback);
        return new FolderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(FolderViewHolder holder, int position) {
        holder.binding.setFolder(mFolderList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mFolderList == null?0:mFolderList.size();
    }

    static class FolderViewHolder extends RecyclerView.ViewHolder {

        final ItemFolderBinding binding;

        public FolderViewHolder(ItemFolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
