package com.example.tareas.task

import com.example.tareas.interfaz.ApiService
import com.example.tareas.response.CheckInResponse
import com.example.tareas.util.Helper
import com.example.tareas.util.RetrofitBuilder
import okhttp3.MultipartBody
import java.io.File

class CheckInTask {
    companion object{
        fun  checkInUser(url: String,word: String, file: File): CheckInResponse {

            val partes = ArrayList<MultipartBody.Part>()
            partes.add(
                MultipartBody.Part.createFormData("word", word))

            partes.add(
                MultipartBody.Part.createFormData("archivo", file.name, Helper.crearMultiparte(file)))

            val call =
                RetrofitBuilder.getRetrofit(url).create(ApiService::class.java).checkIn(partes).execute()
            val user = call.body() as CheckInResponse
            return user
        }
    }
}