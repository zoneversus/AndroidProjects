package com.example.tareas.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NotificacionModel {
    @SerializedName("not_id") @Expose val notID: String?=null
    @SerializedName("not_titulo") @Expose val notTitulo: String?=null
    @SerializedName("not_update") @Expose val notUpdate: String?=null
    @SerializedName("not_desc") @Expose val notDesc: String?=null
    @SerializedName("not_status") @Expose val notStatus: String?=null

}
