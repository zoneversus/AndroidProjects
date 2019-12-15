package com.example.application2.task

import com.example.application2.interfaz.APIService
import com.example.application2.model.MessageResponse

class RecoveryTask {
    companion object{
        fun recoveryUser(url: String, user: String): MessageResponse {
            val call =
                RetrofitBuilder.getRetrofit(url).create(APIService::class.java).recovery(user).execute()
            val message = call.body() as MessageResponse
            return message
        }
    }
}