package com.glee.gbox.recyclerview

import android.os.Bundle
import androidx.lifecycle.MutableLiveData

/**
 *  @author liji
 *  @date  2018/11/19 14:06
 *  description
 */


class RecyclerViewStateLiveData : MutableLiveData<RecyclerViewStateLiveData.STATE>() {
    enum class STATE(var data: Bundle? = null) {
        LOAD_MORE,
        REFRESH,
        IDLE,
        END,
        LOAD_MORE_FAILED,
        REFRESH_FAILED
    }

    init {
        setValue(STATE.IDLE)
    }

    override fun setValue(value: STATE) {
        super.setValue(value)
    }

    override fun postValue(value: STATE) {
        super.postValue(value)
    }
}
