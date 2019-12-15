package com.example.tareas.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CheckInModel {
    @SerializedName("ck_tiemporeal") @Expose
    val tiempoServer: String?=null
    @SerializedName("ck_firmaurl") @Expose
    val urlFirma: String?=null
    @SerializedName("ck_id") @Expose
    val id: String?=null
    @SerializedName("ck_hraenv") @Expose
    val tiempoCel: String?=null

}
