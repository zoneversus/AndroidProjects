package com.example.tareas.response

import com.example.tareas.model.UsuarioModel
import com.google.gson.annotations.SerializedName

data class UserResponse(@SerializedName("valido") var valido:String,
                        @SerializedName("usuario") var user:UsuarioModel,
                        @SerializedName("mensaje") var mensaje: String)