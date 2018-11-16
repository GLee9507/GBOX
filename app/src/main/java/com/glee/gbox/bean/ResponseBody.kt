package com.glee.gbox.bean

/**
 *  @author liji
 *  @date  2018/11/15 22:20
 *  description
 */


data class ResponseBody<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
)