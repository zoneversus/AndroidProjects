package com.example.tareas.response

import com.google.gson.annotations.SerializedName

data class UpdateResponse(@SerializedName("ruta_foto") var url:String,
                       @SerializedName("valido") var valido:String,
                       @SerializedName("mensaje") var mensaje: String)