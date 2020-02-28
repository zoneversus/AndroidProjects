package com.example.mvc.services

import androidx.paging.PagedList
import com.example.mvc.models.PublicacionViewModel
import com.example.mvc.models.UserViewModel
import com.example.mvc.responses.PublicationsResponse
import com.example.mvc.responses.UserResponse

class Task{
    fun getUser():UserViewModel{
        val call = RetrofitBuilder.getRetrofit().
            create(APIService::class.java).getUser().execute()
        val response = call.body() as UserResponse
        return response.user
    }
    fun getPublicaciones():List<PublicacionViewModel>{
     val call=RetrofitBuilder.getRetrofit()
         .create(APIService::class.java).getPublicaciones().execute()
        val response= call.body() as PublicationsResponse
        return response.publicaciones
    }
}