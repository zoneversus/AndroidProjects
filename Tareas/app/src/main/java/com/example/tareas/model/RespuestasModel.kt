package com.example.tareas.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class RespuestasModel {
    @SerializedName("cresp_desc") @Expose
    var respuestaDescripcion: String?=null
    @SerializedName("cresp_id") @Expose
    val RespuestaID: String?=null

}