package com.example.headsupcustomversion

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        retrofit = Retrofit.Builder()
            .baseUrl("https://dojo-recipes.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}