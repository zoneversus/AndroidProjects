package com.example.tareas.response


import com.google.gson.annotations.SerializedName

data class MessageResponse(@SerializedName("valido") var valido:String,
                        @SerializedName("mensaje") var mensaje: String)