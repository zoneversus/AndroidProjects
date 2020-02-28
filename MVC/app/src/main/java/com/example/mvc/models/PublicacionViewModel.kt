package com.example.mvc.models

import com.google.gson.annotations.SerializedName

class PublicacionViewModel(
    @SerializedName("publicacion_id") val publicacionId:Int,
    @SerializedName("publicacion_user") val publicacionNombre:String,
    @SerializedName("publicacion_tipo") val publicacionTipo:Int,
    @SerializedName("publicacion _titulo") val publicacionTitulo:String,
    @SerializedName("publicacion_img") val publicacionImg:String,
    @SerializedName("publicacion_desc") val publicacionDesc:String,
    @SerializedName("publicacion_fecha") val publicacionFecha:String,
    @SerializedName("publicacion_contac") val publicacionContac:String,
    @SerializedName("publicacion_patrocinada") val publicacionPatrocinada: Int
)