package com.glee.gbox.recyclerview

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil

/**
 *  @author liji
 *  @date  2018/11/15 23:08
 *  description
 */
typealias  LoadInitial<T> = (
    params: PageKeyedDataSource.LoadInitialParams<Int>,
    callback: PageKeyedDataSource.LoadInitialCallback<Int, T>
) -> Unit

typealias LoadAfter<T> = (
    params: PageKeyedDataSource.LoadParams<Int>,
    callback: PageKeyedDataSource.LoadCallback<Int, T>
) -> Unit

typealias AreItemsTheSame<T> = (
    oldItem: T, newItem: T
) -> Boolean

typealias AreContentsTheSame<T> = (
    oldItem: T, newItem: T
) -> Boolean

typealias GetChangePayload<T> = (
    oldItem: T, newItem: T
) -> Any?

data class RecyclerViewBinder<T>(
    @LayoutRes
    var layoutResId: Int = 0,
    @IdRes
    var variableId: Int = 0,
    var config: PagedList.Config? = null,
//    private var loadInitial: LoadInitial<T>? = null,
//    private var loadAfter: LoadAfter<T>? = null,
    private var loadData: LoadData<T>? = null,
    private var areItemsTheSame: AreItemsTheSame<T>? = null,
    private var areContentsTheSame: AreContentsTheSame<T>? = null,
    private var getChangePayload: GetChangePayload<T>? = null
) {
    var initialLoadKey: Int = 0

    val diffCallback = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T) =
            areItemsTheSame!!.invoke(oldItem, newItem)


        override fun areContentsTheSame(oldItem: T, newItem: T) =
            areContentsTheSame!!.invoke(oldItem, newItem)

        override fun getChangePayload(oldItem: T, newItem: T): Any? {
            return if (getChangePayload == null) super.getChangePayload(oldItem, newItem)
            else getChangePayload!!.invoke(oldItem, newItem)
        }
    }

    lateinit var listData: LiveData<PagedList<T>>

    fun initLiveData() {
        listData = LivePagedListBuilder<Int, T>(
            object : DataSource.Factory<Int, T>() {
                override fun create() = SimplePageDataSource(loadData!!)
            }, config!!
        ).setInitialLoadKey(initialLoadKey)
            .build()

    }

    //    fun loadInitial(block: LoadInitial<T>) {
//        loadInitial = block
//    }
//
//    fun loadAfter(block: LoadAfter<T>) {
//        loadAfter = block
//    }
    fun loadData(block: LoadData<T>) {
        loadData = block

    }

    fun areItemsTheSame(block: AreItemsTheSame<T>) {
        areItemsTheSame = block
    }

    fun areContentsTheSame(block: AreContentsTheSame<T>) {
        areContentsTheSame = block
    }
}

data class Config(
    var enablePlaceholders: Boolean = true,
    var pageSize: Int = 10,
    var initialLoadSizeHint: Int = pageSize * 3,
    var maxSize: Int = Integer.MAX_VALUE,
    var prefetchDistance: Int = pageSize,
    var initialLoadKey: Int = 0
)

inline fun <T> recyclerViewBinder(block: RecyclerViewBinder<T>.() -> Unit): RecyclerViewBinder<T> =
    RecyclerViewBinder<T>().apply(block).apply {
        initLiveData()
    }


inline fun <T> RecyclerViewBinder<T>.pagedConfig(block: Config.() -> Unit) {
    val tmp = Config().apply(block)
    initialLoadKey = tmp.initialLoadKey
    config = PagedList.Config.Builder()
        .setEnablePlaceholders(tmp.enablePlaceholders)
        .setInitialLoadSizeHint(tmp.initialLoadSizeHint)
        .setMaxSize(tmp.maxSize)
        .setPageSize(tmp.pageSize)
        .setPrefetchDistance(tmp.prefetchDistance)
        .build()
}
