package com.glee.gbox.util.net

import com.glee.gbox.util.AsyncMainExecutor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
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
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
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

fun <T> Call<T>.enqueue(success: (T) -> Unit, error: (Throwable) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            error.invoke(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            val body = response.body()
            if (body == null) onFailure(call, Throwable("null"))
            else success.invoke(body)
        }
    })
}

