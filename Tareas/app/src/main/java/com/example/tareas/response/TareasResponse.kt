package com.example.tareas.response

import com.example.tareas.model.NotificacionModel
import com.example.tareas.model.TareasModel
import com.google.gson.annotations.SerializedName

data class TareasResponse(@SerializedName("valido") var valido:String,
                          @SerializedName("tareas") var tareas:ArrayList<TareasModel>,
                          @SerializedName("mensaje") var mensaje: String)