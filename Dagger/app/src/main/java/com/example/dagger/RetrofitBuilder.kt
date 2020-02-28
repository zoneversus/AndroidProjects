package com.example.dagger

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    companion object{
        /*
        //Creating Auth Interceptor to add api_key query in front of all the requests.
        private val authInterceptor = Interceptor {chain->
            val newUrl = chain.request().url()
                .newBuilder()
                .addQueryParameter("api_key", AppConstants.tmdbApiKey)
                .build()

            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }
        //OkhttpClient for building http request url
        private val tmdbClient = OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .build()*/

        fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://gist.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val apiService : APIService = getRetrofit().create(APIService::class.java)
    }
}