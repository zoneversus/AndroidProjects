package com.example.tareas.response

import com.google.gson.annotations.SerializedName

data class TycResponse(@SerializedName("url_pdf") var url:String,
                       @SerializedName("valido") var valido:String,
                           @SerializedName("mensaje") var mensaje: String)