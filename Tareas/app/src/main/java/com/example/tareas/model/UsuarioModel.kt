package com.example.tareas.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UsuarioModel {
    @SerializedName("usr_rutafoto") @Expose
    var usr_ruta: String?=null
    @SerializedName("usr_email") @Expose
    val usr_email: String?=null
    @SerializedName("usr_id") @Expose
    val usr_id: String?=null
    @SerializedName("usu_id") @Expose
    val usu_id: String?=null
    @SerializedName("usr_nombre") @Expose
    var usr_nombre: String?=null
}