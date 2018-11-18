package com.glee.gbox.main

import android.os.Looper
import android.provider.Contacts
import android.util.Log
import com.glee.gbox.BR
import com.glee.gbox.BaseViewModel
import com.glee.gbox.R
import com.glee.gbox.bean.DatasItem
import com.glee.gbox.recyclerview.pagedConfig
import com.glee.gbox.recyclerview.recyclerViewBinder
import com.glee.gbox.util.net.NET
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.Continuation

/**
 *  @author liji
 *  @date  2018/11/15 18:02
 *  description
 */


class MainViewModel : BaseViewModel() {
    companion object {
        const val TAG = "MainViewModel"
    }

    val recyclerViewBinder =
        recyclerViewBinder<DatasItem> {
            layoutResId = R.layout.recycler_item_main

            variableId = BR.data

            pagedConfig {
                pageSize = 20
            }
            loadInitial = { _, callback ->
                //                Log.d(TAG, Thread.currentThread().name)
//                NET.articleList(0).enqueue({
//                    callback.onResult(it.data.datas!!, -1, 1)
//                }, {
//
//                })
                val launch = GlobalScope.launch {
                    val responseBody = NET.articleList(0)
                        .await()
                    callback.onResult(responseBody.data.datas!!, -1, 1)
                }
            }
            loadAfter = { params, callback ->
                val launch = GlobalScope.launch {
                    val responseBody = NET.articleList(params.key)
                        .await()
                    callback.onResult(responseBody.data.datas!!, params.key + 1)
                }
            }
            areItemsTheSame = { oldItem, newItem ->
                oldItem.id == newItem.id
            }
            areContentsTheSame = { oldItem, newItem ->
                oldItem.id == newItem.id
            }
        }

    override fun initData() {

    }
}