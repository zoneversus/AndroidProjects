package com.example.tareas.response

import com.example.tareas.model.NotificacionModel
import com.google.gson.annotations.SerializedName


data class NotificacionResponse(@SerializedName("valido") var valido:String,
                        @SerializedName("notificaciones") var notificacion:ArrayList<NotificacionModel>,
                        @SerializedName("mensaje") var mensaje: String)