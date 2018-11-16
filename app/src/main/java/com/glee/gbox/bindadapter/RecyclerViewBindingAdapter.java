package com.glee.gbox.bindadapter;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.glee.gbox.recyclerview.LiveRecyclerAdapter;
import com.glee.gbox.recyclerview.RecyclerViewBinder;
import com.glee.gbox.recyclerview.SimaplePagedAdapter;

import java.util.List;

/**
 * @author liji
 * @date 2018/11/15 22:56
 * description
 */


public class RecyclerViewBindingAdapter {
    @BindingAdapter({"app:binder", "app:data"})
    public static void bind(RecyclerView recyclerView, RecyclerViewBinder binder, PagedList data) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            adapter = new SimaplePagedAdapter(binder);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setAdapter(adapter);
        }
        if (adapter instanceof SimaplePagedAdapter) {
            ((SimaplePagedAdapter) adapter).submitList(data);
        }
    }
}
