package com.example.tareas.task

import com.example.tareas.interfaz.ApiService
import com.example.tareas.response.MessageResponse
import com.example.tareas.response.NotificacionResponse
import com.example.tareas.response.TareaResponse
import com.example.tareas.response.TareasResponse
import com.example.tareas.util.RetrofitBuilder

class TareasTask{
    companion object{

        fun getTareas(url: String, word: String): TareasResponse {
            val call =
                RetrofitBuilder.getRetrofit(url).create(ApiService::class.java).getTareas(word).execute()
            val user = call.body() as TareasResponse
            return user
        }

        fun updateTareas(url: String, word: String): TareaResponse {
            val call =
                RetrofitBuilder.getRetrofit(url).create(ApiService::class.java).updateTarea(word).execute()
            val user = call.body() as TareaResponse
            return user
        }

        fun endTarea(url: String, word: String): MessageResponse {
            val call =
                RetrofitBuilder.getRetrofit(url).create(ApiService::class.java).endTarea(word).execute()
            val user = call.body() as MessageResponse
            return user
        }

    }
}