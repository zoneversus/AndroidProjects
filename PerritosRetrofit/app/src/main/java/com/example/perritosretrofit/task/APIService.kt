package com.example.perritosretrofit.task

import com.example.perritosretrofit.Model.DogModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface APIService {
    @GET
    fun getCharacterByName(@Url url:String): Call<DogModel>
}
