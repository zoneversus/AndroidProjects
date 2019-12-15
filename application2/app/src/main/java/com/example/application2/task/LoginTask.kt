package com.example.application2.task

import com.example.application2.interfaz.APIService
import com.example.application2.model.UserResponse

class LoginTask {
companion object{

     fun loginUser(url: String, user: String, nip: String): UserResponse {
        val call =
           RetrofitBuilder.getRetrofit(url).create(APIService::class.java).login( user, nip).execute()
        val user = call.body() as UserResponse
        return user
    }
}
}