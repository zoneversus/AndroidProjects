package com.example.mvc.services

import com.example.mvc.models.UserViewModel
import com.example.mvc.responses.PublicationsResponse
import com.example.mvc.responses.UserResponse
import retrofit2.Call
import retrofit2.http.GET

interface APIService{
    @GET("zoneversus/5b8339a570ccf89bc54c83e54faf325e/raw/2a163a58a7ad3c9db18757547c98add99e1c96af/user")
    fun getUser():Call<UserResponse>
    @GET("zoneversus/25dc01e38edf37a10bcf921a4a76cbdd/raw/58fef4b2f95e52aa1433fdec3eeabb6c42ea84f6/publicaciones")
    fun getPublicaciones():Call<PublicationsResponse>
}