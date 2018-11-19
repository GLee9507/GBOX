package com.glee.gbox.recyclerview;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;
import com.glee.gbox.util.AsyncMainExecutor;

import java.util.List;

/**
 * @author liji
 * @date 11/7/2018 2:39 PM
 * description
 */


public class LiveRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final AsyncListDiffer<T> asyncListDiffer;
    @LayoutRes
    private final int layoutResId;
    private LayoutInflater layoutInflater;
    @IdRes
    private final int variableId;
    private final Listing<T> listing;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    @SuppressLint("RestrictedApi")
    public LiveRecyclerAdapter(Listing<T> listing) {
        this.listing = listing;
        this.layoutResId = listing.getLayoutResId();
        this.variableId = listing.getVariableId();
        asyncListDiffer = new AsyncListDiffer<>(
                new AdapterListUpdateCallback(this),
                new AsyncDifferConfig.Builder<>(listing.getDiff())
                        .setMainThreadExecutor(AsyncMainExecutor.Companion.create()).build()
        );

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        layoutInflater = LayoutInflater.from(recyclerView.getContext());
        progressBar = new ProgressBar(recyclerView.getContext());
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    public void submitList(List<T> data) {
        asyncListDiffer.submitList(data);
        listing.setPageNum(listing.getPageNum() + 1);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == asyncListDiffer.getCurrentList().size()) {
            return new LoadMoreHolder(progressBar);
        } else {
            return new LiveHolder(DataBindingUtil.inflate(layoutInflater, layoutResId, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof LiveRecyclerAdapter.LiveHolder) {
            ViewDataBinding binding = ((LiveHolder) holder).binding;
            binding.setVariable(variableId, asyncListDiffer.getCurrentList().get(i));
            binding.executePendingBindings();
        }
        autoLoadMore(i);
    }

    private void autoLoadMore(int position) {
        if (position < getItemCount()-2) {
            return;
        }
        listing.getLoadData().invoke(listing.getPageNum());
    }


    @Override
    public int getItemCount() {
        return asyncListDiffer.getCurrentList().size() + 1;
    }

    private static class LiveHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        LiveHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private static class LoadMoreHolder extends RecyclerView.ViewHolder {

        public LoadMoreHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}



