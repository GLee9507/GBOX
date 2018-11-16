package com.glee.gbox.main

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.glee.gbox.BR
import com.glee.gbox.R
import com.glee.gbox.BaseViewModel
import com.glee.gbox.bean.DatasItem
import com.glee.gbox.recyclerview.RecyclerViewBinder
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
    val recyclerViewBinder =
        recyclerViewBinder<DatasItem> {
            layoutResId = R.layout.recycler_item_main

            variableId = BR.data

            pagedConfig {
                pageSize = 20
            }
            loadInitial = { _, callback ->
                NET.articleList(0).enqueue({
                    callback.onResult(it.data.datas!!, -1, 1)
                }, {

                })
            }
            loadAfter = { params, callback ->
                NET.articleList(params.key).enqueue({
                    callback.onResult(it.data.datas!!, params.key + 1)
                }, {

                })
            }
            areItemsTheSame = { oldItem, newItem ->
                oldItem.id==newItem.id
            }
            areContentsTheSame = { oldItem, newItem ->
                oldItem.id==newItem.id
            }
        }

    override fun initData() {

    }
}