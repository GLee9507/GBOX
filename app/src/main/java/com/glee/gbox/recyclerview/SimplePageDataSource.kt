package com.glee.gbox.recyclerview

import androidx.paging.PageKeyedDataSource

/**
 * @author liji
 * @date 2018/11/16 13:50
 * description
 */


class SimplePageDataSource<T>(
    private val loadInitial: LoadInitial<T>,
    private val loadAfter: LoadAfter<T>
) : PageKeyedDataSource<Int, T>() {

    override fun loadInitial(
        params: PageKeyedDataSource.LoadInitialParams<Int>,
        callback: PageKeyedDataSource.LoadInitialCallback<Int, T>
    ) {
        loadInitial.invoke(params, callback)
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
        loadAfter.invoke(params, callback)
    }
}
