package com.example.ejemplocoroutines_livedata.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val wS: Webservice by lazy {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .client(
            OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor.apply {
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(Webservice::class.java)
}

val coroutineWS: CoroutineWebservice by lazy {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .client(
            OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor.apply {
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(CoroutineWebservice::class.java)
}