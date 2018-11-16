package com.glee.gbox

import androidx.lifecycle.ViewModel

/**
 *  @author liji
 *  @date  2018/11/15 20:05
 *  description
 */


open class BaseViewModel : ViewModel() {
    init {
        initData()
    }

   open fun initData() {}
}