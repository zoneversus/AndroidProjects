package com.example.tareas.interfaz

import com.example.tareas.response.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("GeneralData")
    fun login(
        @Query("word") word: String): Call<UserResponse>
    @GET("GeneralData")
    fun Tyc(
        @Query("word") word: String): Call<TycResponse>
    @GET("GeneralData")
    fun register(
        @Query("word") word: String): Call<UserResponse>
    @GET("GeneralData")
    fun recovery(
        @Query("word") word: String): Call<MessageResponse>
    @GET("GeneralData")
    fun updateNotificacion(
        @Query("word") word: String): Call<MessageResponse>
    @GET("GeneralData")
    fun getNotificaciones(
        @Query("word") word: String): Call<NotificacionResponse>
    @GET("GeneralData")
    fun getTareas(
        @Query("word") word: String): Call<TareasResponse>
    @GET("GeneralData")
    fun endTarea(
        @Query("word") word: String): Call<MessageResponse>
    @GET("GeneralData")
    fun updateTarea(
        @Query("word") word: String): Call<TareaResponse>
    @GET("GeneralData")
    fun getEncuesta(
        @Query("word") word: String): Call<EncuestaResponse>
    @GET("GeneralData")
    fun updateEncuesta(
        @Query("word") word: String): Call<MessageResponse>
    @Multipart
    @POST("GeneralData")
    fun checkIn(
       @Part file: ArrayList<MultipartBody.Part>): Call<CheckInResponse>
    @Multipart
    @POST("GeneralData")
    fun update(
        @Part file: ArrayList<MultipartBody.Part>): Call<UpdateResponse>

}