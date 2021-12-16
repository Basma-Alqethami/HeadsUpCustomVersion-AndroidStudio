package com.example.headsupcustomversion

import retrofit2.Call
import retrofit2.http.*


interface API {
    @GET("celebrities/")
    fun getData(): Call<Celebrities>

    @POST("celebrities/")
    fun postData(@Body userData: CelebritiesItem): Call<CelebritiesItem>

    @PUT("/celebrities/{id}")
    fun updateData(@Path("id") id: Int, @Body userData: CelebritiesItem): Call<CelebritiesItem>

    @DELETE("/celebrities/{id}")
    fun deleteData(@Path("id") id: Int): Call<Void>
}