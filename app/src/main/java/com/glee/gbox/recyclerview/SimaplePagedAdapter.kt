package com.glee.gbox.recyclerview

import android.annotation.SuppressLint
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.RecyclerView
import com.glee.gbox.util.AsyncMainExecutor

/**
 * @author liji
 * @date 2018/11/16 13:24
 * description
 */


class SimaplePagedAdapter<T>
@SuppressLint("RestrictedApi")
constructor(private val binder: RecyclerViewBinder<T>) : PagedListAdapter<T, LiveHolder>
    (
    AsyncDifferConfig.Builder<T>(binder.diffCallback)
        .setMainThreadExecutor(AsyncMainExecutor.create())
        .build()
) {
    private lateinit var inflater:LayoutInflater

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.inflater = LayoutInflater.from(recyclerView.context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveHolder {
        return LiveHolder(DataBindingUtil.inflate(inflater, binder.layoutResId, parent, false))
    }

    override fun onBindViewHolder(holder: LiveHolder, position: Int) {
        val binding = holder.binding
        binding.setVariable(binder.variableId, getItem(position))
        binding.executePendingBindings()
    }
}
