package com.example.application2.interfaz

import com.example.application2.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface APIService {
    @GET("DataWS?ApiCall=getLoginUsr")
    fun login(
        @Query("email") email: String,
              @Query("nip") nip: String): Call<UserResponse>

    @GET("DataWS?ApiCall=getProductos")
    fun getProducts(): Call<ProductsResponse>

    @GET("DataWS?ApiCall=recoveryPSw")
    fun recovery(
        @Query("email") email: String): Call<MessageResponse>

    @GET("DataWS?ApiCall=setProdOrder")
    fun purchase(
        @Query("id_producto") id_producto: String): Call<PedidoResponse>

    @GET("DataWS?ApiCall=setRegisterUsr")
    fun registerNoImage(
        @Query("email") email: String,
        @Query("nombre") nombre: String,
        @Query("app") apellidoPaterno: String,
        @Query("apm") apellidoMaterno: String,
        @Query("nip") nip: String): Call<RegistroResponse>

    @GET("DataWS?ApiCall=updateUsr")
    fun updateNoImage(
        @Query("usrid") usrid: String,
        @Query("email") email: String,
        @Query("nombre") nombre: String,
        @Query("app") apellidoPaterno: String,
        @Query("apm") apellidoMaterno: String,
        @Query("nip") nip: String): Call<MessageResponse>

    @Multipart
    @POST("DataWS")
    fun register(
        @Part file: ArrayList<MultipartBody.Part>): Call<RegistroResponse>

    @Multipart
    @POST("DataWS")
    fun updateProfile(
        @Part file: ArrayList<MultipartBody.Part>): Call<RegistroResponse>

}
