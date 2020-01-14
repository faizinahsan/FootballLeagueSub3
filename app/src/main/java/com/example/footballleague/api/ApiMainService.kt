package com.example.footballleague.api

import android.app.Application
import com.example.footballleague.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiMainService : Application() {

    private val client = OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }).readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30,TimeUnit.SECONDS)
        .build()
    private val retrofit = Retrofit.Builder().baseUrl("https://www.thesportsdb.com/").client(client).addConverterFactory(GsonConverterFactory.create()).build()
    val service: APIService = retrofit.create(APIService::class.java)
}