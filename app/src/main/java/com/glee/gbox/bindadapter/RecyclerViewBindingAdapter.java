package com.glee.gbox.bindadapter;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.glee.gbox.recyclerview.Listing;
import com.glee.gbox.recyclerview.LiveRecyclerAdapter;

import java.util.List;

/**
 * @author liji
 * @date 2018/11/15 22:56
 * description
 */


public class RecyclerViewBindingAdapter {
    @BindingAdapter({"app:binder", "app:data"})
    public static void bind(RecyclerView recyclerView, Listing listing, List data) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            adapter = new LiveRecyclerAdapter(listing);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setAdapter(adapter);
        }
        if (adapter instanceof LiveRecyclerAdapter) {
            ((LiveRecyclerAdapter) adapter).submitList(data);
        }
    }
}
