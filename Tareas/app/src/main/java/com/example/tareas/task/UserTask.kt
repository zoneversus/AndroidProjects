package com.example.tareas.task

import com.example.tareas.interfaz.ApiService
import com.example.tareas.response.MessageResponse
import com.example.tareas.response.TycResponse
import com.example.tareas.response.UpdateResponse
import com.example.tareas.response.UserResponse
import com.example.tareas.util.Helper
import com.example.tareas.util.RetrofitBuilder
import okhttp3.MultipartBody
import java.io.File

class UserTask{
    companion object{
        fun loginUser(url: String, word: String): UserResponse {
            val call =
                RetrofitBuilder.getRetrofit(url).create(ApiService::class.java).login(word).execute()
            val user = call.body() as UserResponse
            return user
        }
        fun registerUser(url: String, word: String): UserResponse {
            val call =
                RetrofitBuilder.getRetrofit(url).create(ApiService::class.java).register(word).execute()
            val user = call.body() as UserResponse
            return user
        }
        fun recoveryUser(url: String, word: String): MessageResponse {
            val call =
                RetrofitBuilder.getRetrofit(url).create(ApiService::class.java).recovery(word).execute()
            val user = call.body() as MessageResponse
            return user
        }
        fun updateUser(url: String, word: String,file: File): UpdateResponse {
            val partes = ArrayList<MultipartBody.Part>()
            partes.add(
                MultipartBody.Part.createFormData("word", word))
            partes.add(
                MultipartBody.Part.createFormData("archivo", file.name, Helper.crearMultiparte(file)))

            val call =
                RetrofitBuilder.getRetrofit(url).create(ApiService::class.java).update(partes).execute()
            val user = call.body() as UpdateResponse
            return user
        }
        fun tycUser(url: String, word: String): TycResponse {
            val call =
                RetrofitBuilder.getRetrofit(url).create(ApiService::class.java).Tyc(word).execute()
            val user = call.body() as TycResponse
            return user
        }
    }
}