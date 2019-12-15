package com.example.tareas.response

import com.example.tareas.model.TareasModel
import com.google.gson.annotations.SerializedName

data class TareaResponse(@SerializedName("tarea") var tarea:TareasModel,
                       @SerializedName("valido") var valido:String,
                       @SerializedName("mensaje") var mensaje: String)