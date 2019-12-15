package com.example.tareas.task

import com.example.tareas.interfaz.ApiService
import com.example.tareas.response.EncuestaResponse
import com.example.tareas.response.MessageResponse
import com.example.tareas.response.TareasResponse
import com.example.tareas.util.RetrofitBuilder

class EncuestaTask{
    companion object{

        fun getEncuesta(url: String, word: String): EncuestaResponse {
            val call =
                RetrofitBuilder.getRetrofit(url).create(ApiService::class.java).getEncuesta(word).execute()
            val user = call.body() as EncuestaResponse
            return user
        }

        fun updateEncuesta(url: String, word: String): MessageResponse {
            val call =
                RetrofitBuilder.getRetrofit(url).create(ApiService::class.java).updateEncuesta(word).execute()
            val user = call.body() as MessageResponse
            return user
        }

    }
}