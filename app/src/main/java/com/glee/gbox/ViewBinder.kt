package com.glee.gbox

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

/**
 *  @author liji
 *  @date  2018/11/15 21:27
 *  description
 */


data class ViewBinder(
    @LayoutRes
    val layoutResId: Int,
    @IdRes
    val viewModelId: Int
)