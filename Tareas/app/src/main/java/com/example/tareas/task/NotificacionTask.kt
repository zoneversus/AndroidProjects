package com.example.tareas.task

import com.example.tareas.interfaz.ApiService
import com.example.tareas.response.MessageResponse
import com.example.tareas.response.NotificacionResponse
import com.example.tareas.util.RetrofitBuilder

class NotificacionTask{
    companion object{

        fun getNotificaciones(url: String, word: String): NotificacionResponse {
            val call =
                RetrofitBuilder.getRetrofit(url).create(ApiService::class.java).getNotificaciones(word).execute()
            val user = call.body() as NotificacionResponse
            return user
        }

        fun updateNotificacion(url: String, word: String):MessageResponse{
            val call =
                RetrofitBuilder.getRetrofit(url).create(ApiService::class.java).updateNotificacion(word).execute()
            val user = call.body() as MessageResponse
            return user
        }

    }
}