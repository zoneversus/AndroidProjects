package com.example.tareas.response

import com.example.tareas.model.CheckInModel
import com.google.gson.annotations.SerializedName

data class CheckInResponse(@SerializedName("valido") var valido:String,
                           @SerializedName("registro_ck") var registro: CheckInModel,
                           @SerializedName("mensaje") var mensaje: String)