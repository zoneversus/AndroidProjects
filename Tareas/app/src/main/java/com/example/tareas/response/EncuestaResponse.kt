package com.example.tareas.response

import com.example.tareas.model.EncuestaModel
import com.example.tareas.model.TareasModel
import com.google.gson.annotations.SerializedName

data class EncuestaResponse(@SerializedName("valido") var valido:String,
                          @SerializedName("encuesta") var tareas:ArrayList<EncuestaModel>,
                          @SerializedName("mensaje") var mensaje: String)