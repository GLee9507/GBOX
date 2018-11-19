package com.glee.gbox.recyclerview

import androidx.paging.PageKeyedDataSource

/**
 * @author liji
 * @date 2018/11/16 13:50
 * description
 */

typealias LoadData<T> = (
    pageNum: Int,
    callback: (List<T>) -> Unit,
    handleError: (pageNum: Int) -> Unit
) -> Unit

class SimplePageDataSource<T>(
//    private val loadInitial: LoadInitial<T>,
//    private val loadAfter: LoadAfter<T>,
    private val loadData: LoadData<T>
) : PageKeyedDataSource<Int, T>() {
    override fun loadInitial(
        params: PageKeyedDataSource.LoadInitialParams<Int>,
        callback: PageKeyedDataSource.LoadInitialCallback<Int, T>
    ) {
        invalidate()
        loadData.invoke(0, {
            callback.onResult(it, -1, 1)
        }, {

        })
    }

    override fun loadBefore(
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, T>
    ) {

    }

    override fun loadAfter(
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, T>
    ) {
        loadData.invoke(params.key, {
            callback.onResult(it, params.key + 1)
        }, {

        })
    }
}
