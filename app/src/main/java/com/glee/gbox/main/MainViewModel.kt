package com.glee.gbox.main

import com.glee.gbox.BR
import com.glee.gbox.BaseViewModel
import com.glee.gbox.R
import com.glee.gbox.bean.DatasItem
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

    val recyclerViewBinder =
        recyclerViewBinder<DatasItem> {
            layoutResId = R.layout.recycler_item_main

            variableId = BR.data

            pagedConfig {
                pageSize = 20
            }
            loadData { pageNum, callback ->
                NET.articleList(pageNum)
                    .enqueue {
                        onResponse {
                            callback.invoke(it.data.datas!!)
                        }
                        onFailure {

                        }
                    }
            }
            areItemsTheSame { oldItem, newItem ->
                oldItem.id == newItem.id
            }
            areContentsTheSame { oldItem, newItem ->
                oldItem.id == newItem.id
            }

        }

    override fun initData() {

    }
}