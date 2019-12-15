package com.example.application2.task

import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import com.example.application2.interfaz.APIService
import com.example.application2.model.RegistroResponse
import com.example.application2.utils.Validation
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class RegisterTask {
    companion object{
        fun registerUser(url: String, user: String, file : File, nombre:String, app:String, apm:String, nip: String): RegistroResponse {

            val partes = ArrayList<MultipartBody.Part>()
            partes.add(
                MultipartBody.Part.createFormData("ApiCall", "setRegisterUsr"))
            partes.add(
                MultipartBody.Part.createFormData("email", user))
            partes.add(
                MultipartBody.Part.createFormData("nombre", nombre))
            partes.add(
                MultipartBody.Part.createFormData("app", app))
            partes.add(
                MultipartBody.Part.createFormData("apm", apm))
            partes.add(
                MultipartBody.Part.createFormData("nip", nip))
            partes.add(
                MultipartBody.Part.createFormData("archivo", file.name, Validation.crearMultiparte(file)))

            val call =
                RetrofitBuilder.getRetrofit(url).create(APIService::class.java).register(partes).execute()

            val usuario = call.body() as RegistroResponse
            return usuario
        }

        fun registerUserNoImage(url: String, user: String, nombre:String, app:String, apm:String, nip: String): RegistroResponse {
            val call =
                RetrofitBuilder.getRetrofit(url).create(APIService::class.java).registerNoImage( user,
                    nombre,app,apm,nip).execute()

            val usuario = call.body() as RegistroResponse
            return usuario
        }


    }
}