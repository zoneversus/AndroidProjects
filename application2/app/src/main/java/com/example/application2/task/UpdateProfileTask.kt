package com.example.application2.task

import com.example.application2.interfaz.APIService
import com.example.application2.model.MessageResponse
import com.example.application2.model.RegistroResponse
import com.example.application2.model.UserResponse
import com.example.application2.utils.Validation
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UpdateProfileTask {
    companion object{
        fun updateUser(url: String, file: File, userid: String, email: String, name:String, app:String, apm:String, nip:String): RegistroResponse {

            val partes = ArrayList<MultipartBody.Part>()
            partes.add(
                MultipartBody.Part.createFormData("ApiCall", "editRegisterUsr"))
            partes.add(
                MultipartBody.Part.createFormData("usrid", userid))
            partes.add(
            MultipartBody.Part.createFormData("email", email))
            partes.add(
                MultipartBody.Part.createFormData("nombre", name))
            partes.add(
                MultipartBody.Part.createFormData("app", app))
            partes.add(
                MultipartBody.Part.createFormData("apm", apm))
            partes.add(
                MultipartBody.Part.createFormData("nip", nip))
            partes.add(
                MultipartBody.Part.createFormData("archivo", file.name, Validation.crearMultiparte(file)))

            val call =
                RetrofitBuilder.getRetrofit(url).create(APIService::class.java).updateProfile(partes).execute()
            val user = call.body() as RegistroResponse
            return user
        }

        fun updateUserNoImage(url: String, userid: String, email: String, name:String, app:String, apm:String, nip:String): MessageResponse {
            val call =
                RetrofitBuilder.getRetrofit(url).create(APIService::class.java).updateNoImage(userid,
                    email,name,app,apm,nip).execute()
            val user = call.body() as MessageResponse
            return user
        }
    }
}