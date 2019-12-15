package com.example.tareas.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class EncuestaModel {
    @SerializedName("cpreg_id") @Expose
    var preg_id: String?=null
    @SerializedName("cpreg_tipo") @Expose
    val preg_tipo: String?=null
    @SerializedName("cpreg_titulo") @Expose
    val preg_titulo: String?=null
    @SerializedName("respuestas") @Expose
    val ListaRespuestas: ArrayList<RespuestasModel>?=null
}