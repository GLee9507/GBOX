package com.glee.gbox.util.net

import com.glee.gbox.bean.ArticleData
import com.glee.gbox.bean.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  @author liji
 *  @date  2018/11/15 22:14
 *  description
 */


interface Api {
//    @GET("/article/list/{pageNum}/json")
//    suspend fun articleList(@Path("pageNum") pageNum: Int): ResponseBody<ArticleData>

    @GET("/article/list/{pageNum}/json")
    fun articleList(@Path("pageNum") pageNum: Int): Call<ResponseBody<ArticleData>>
}