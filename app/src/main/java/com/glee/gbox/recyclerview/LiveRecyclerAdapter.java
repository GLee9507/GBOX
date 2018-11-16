package com.glee.gbox.recyclerview;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
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


public class LiveRecyclerAdapter<T> extends RecyclerView.Adapter<LiveHolder> {
    private final AsyncListDiffer<T> asyncListDiffer;
    @LayoutRes
    private final int layoutResId;
    private LayoutInflater layoutInflater;
    @IdRes
    private final int variableId;
    private final RecyclerViewBinder<T> binder;

    @SuppressLint("RestrictedApi")
    public LiveRecyclerAdapter(RecyclerViewBinder<T> binder) {
        this.binder = binder;
        this.layoutResId = binder.getLayoutResId();
        this.variableId = binder.getVariableId();
        asyncListDiffer = new AsyncListDiffer<>(
                new AdapterListUpdateCallback(this),
                new AsyncDifferConfig.Builder<>(binder.getDiffCallback())
                        .setMainThreadExecutor(AsyncMainExecutor.Companion.create()).build()
        );

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        layoutInflater = LayoutInflater.from(recyclerView.getContext());
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void submitList(List<T> data) {
        asyncListDiffer.submitList(data);
    }

    @NonNull
    @Override
    public LiveHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LiveHolder(DataBindingUtil.inflate(layoutInflater, layoutResId, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LiveHolder liveHolder, int i) {
        if (i == asyncListDiffer.getCurrentList().size() - 2) {
//            loadMore();
        }
        liveHolder.binding.setVariable(variableId, asyncListDiffer.getCurrentList().get(i));
        liveHolder.binding.executePendingBindings();
    }

    int page = 0;

//    private void loadMore() {
//        binder.getLoadMore().invoke(++page);
//    }

    @Override
    public int getItemCount() {
        return asyncListDiffer.getCurrentList().size();
    }
}



