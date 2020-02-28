package com.example.dagger

import retrofit2.await

class UserTask :Task(){

    suspend fun getUser(api : APIService) : UserResponse?{

      val user = safeApiCall(
            call = {api.getLogin()},
            errorMessage = "getting"
        )

        return user

    }
}