package com.glee.gbox.util

import android.annotation.SuppressLint
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message

import java.util.concurrent.Executor

/**
 * @author liji
 * @date 2018/11/12 9:27
 * description 异步主线程调度器
 */


class AsyncMainExecutor @SuppressLint("NewApi")
private constructor() : Handler.Callback, Executor {

    private val handler = Handler(Looper.getMainLooper(), this)
    private var async = true

    init {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            async = false
        } else if (async && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
            val message = Message.obtain()
            try {
                message.isAsynchronous = true
            } catch (e: NoSuchMethodError) {
                async = false
                e.printStackTrace()
            }

            message.recycle()
        }
    }

    override fun handleMessage(msg: Message): Boolean {
        if (msg.what == RUN) {
            (msg.obj as Runnable).run()
            return true
        }
        return false
    }

    @SuppressLint("NewApi")
    override fun execute(command: Runnable) {
        val message = handler.obtainMessage(RUN)
        message.obj = command
        if (async) {
            message.isAsynchronous = true
        }
        message.sendToTarget()
    }

    companion object {
        private const val RUN = 100
        public fun create() = AsyncMainExecutor()
    }
}
