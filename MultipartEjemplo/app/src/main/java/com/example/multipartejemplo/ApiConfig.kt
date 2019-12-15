package com.example.multipartejemplo

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiConfig {
    @Multipart
    @POST("DataWS")
    fun updateProfile(@Part partMap: List<MultipartBody.Part> ): Call<ServerResponse>
}
