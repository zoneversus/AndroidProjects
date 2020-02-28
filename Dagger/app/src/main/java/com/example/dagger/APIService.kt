package com.example.dagger

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET




interface APIService{
    @GET("zoneversus/5b8339a570ccf89bc54c83e54faf325e/raw/2a163a58a7ad3c9db18757547c98add99e1c96af/user")
    suspend fun getLogin(): Response<UserResponse>
}