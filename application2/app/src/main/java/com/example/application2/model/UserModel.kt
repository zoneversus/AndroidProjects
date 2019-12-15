package com.example.application2.model

import com.google.gson.annotations.SerializedName

data class UserModel(@SerializedName("usr_id") var id:String,
                     @SerializedName("usr_rutafoto") var ruta: String,
                     @SerializedName("usr_email") var email: String,
                     @SerializedName("usr_nip") var nip: String,
                     @SerializedName("usr_nombre") var nombre: String,
                     @SerializedName("usr_app") var app: String,
                     @SerializedName("usr_apm") var apm: String)
