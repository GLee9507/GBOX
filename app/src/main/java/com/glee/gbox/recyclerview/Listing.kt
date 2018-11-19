package com.glee.gbox.recyclerview

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil

/**
 *  @author liji
 *  @date  2018/11/19 15:57
 *  description
 */


class Listing<T> {
    @LayoutRes
    var layoutResId: Int = 0
    @IdRes
    var variableId: Int = 0

    var pageNum: Int = 0

    var preLoadSize: Int = -1

    var loadData: ((pageNum: Int) -> Unit)? = null

    val listData = MutableLiveData<List<T>>()

    var diff: DiffUtil.ItemCallback<T>? = null

    val listState = RecyclerViewStateLiveData()

    fun loadInitial() {
        loadData!!.invoke(pageNum)
    }


}
