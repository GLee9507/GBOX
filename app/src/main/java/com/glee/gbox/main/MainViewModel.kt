package com.glee.gbox.main

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.glee.gbox.BR
import com.glee.gbox.BaseViewModel
import com.glee.gbox.R
import com.glee.gbox.bean.DatasItem
import com.glee.gbox.recyclerview.Listing
import com.glee.gbox.recyclerview.pagedConfig
import com.glee.gbox.recyclerview.recyclerViewBinder
import com.glee.gbox.util.net.NET
import com.glee.gbox.util.net.enqueue

/**
 *  @author liji
 *  @date  2018/11/15 18:02
 *  description
 */


class MainViewModel : BaseViewModel() {
    companion object {
        const val TAG = "MainViewModel"
    }

    //    val recyclerViewBinder =
//        recyclerViewBinder<DatasItem> {
//            layoutResId = R.layout.recycler_item_main
//
//            variableId = BR.data
//
//            pagedConfig {
//                pageSize = 20
//            }
//            loadData { pageNum, callback, handle ->
//                NET.articleList(pageNum)
//                    .enqueue {
//                        onResponse {
//                            callback.invoke(it.data.datas!!)
//                        }
//                        onFailure {
//                            handle.invoke()
//                        }
//                    }
//            }
//            areItemsTheSame { oldItem, newItem ->
//                oldItem.id == newItem.id
//            }
//            areContentsTheSame { oldItem, newItem ->
//                oldItem.id == newItem.id
//            }
//
//        }
    val listing = Listing<DatasItem>().apply {
        layoutResId = R.layout.recycler_item_main
        variableId = BR.data
        loadData = { it ->
            NET.articleList(it)
                .enqueue {
                    onResponse {
                        val liveData: MutableLiveData<List<DatasItem>> = this@apply.listData
                        var list = liveData.value
                        if (list == null) {
                            list = listOf()
                        }
                        val mutableList = list.toMutableList().apply { addAll(it.data.datas!!) }
                        liveData.value = mutableList
                    }
                    onFailure {
                    }
                }
        }
        diff = object : DiffUtil.ItemCallback<DatasItem>() {
            override fun areItemsTheSame(oldItem: DatasItem, newItem: DatasItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DatasItem, newItem: DatasItem): Boolean {
                return oldItem.id == newItem.id
            }

        }

        this.preLoadSize = 18
    }


    override fun initData() {

    }
}