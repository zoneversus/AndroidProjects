package com.example.ejemplocoroutines_livedata.data

import retrofit2.http.GET
import retrofit2.http.Path

//si se quiere capturar el error
//https://stackoverflow.com/questions/57323111/kotlin-android-retrofit-2-6-0-with-coroutines-error-handling
interface CoroutineWebservice {

    @GET("/todos/{id}")
    suspend fun getTodo(@Path(value = "id") todoId: Int): Todo

}