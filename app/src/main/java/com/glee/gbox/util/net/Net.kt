package com.glee.gbox.util.net

import com.glee.gbox.util.AsyncMainExecutor
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.internal.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 *  @author liji
 *  @date  2018/11/15 22:00
 *  description
 */


val NET: Api by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://www.wanandroid.com")
        .callbackExecutor(AsyncMainExecutor.create())
//        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(
            OkHttpClient.Builder()
                .dispatcher(Dispatcher(IOEXECUTORSERVICE))
                .build()
        ).build()
        .create(Api::class.java)
}

val IOEXECUTORSERVICE by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    ThreadPoolExecutor(
        0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
        SynchronousQueue(), Util.threadFactory("G-BOX ThreadPool", false)
    )
}
typealias OnResponse<T> = (response: T) -> Unit
typealias OnFailure = (t: Throwable) -> Unit


inline fun <T> Call<T>.enqueue(block: CallBackWrapper<T>.() -> Unit) {
    val backWrapper = CallBackWrapper<T>().apply(block)
    enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            backWrapper.onFailure!!.invoke(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            val body = response.body()!!
            backWrapper.onResponse!!.invoke(body)
        }
    })
}

class CallBackWrapper<T> {
    var onResponse: OnResponse<T>? = null
    var onFailure: OnFailure? = null

    fun onResponse(block: OnResponse<T>) {
        onResponse = block
    }

    fun onFailure(block: OnFailure) {
        onFailure = block
    }
}