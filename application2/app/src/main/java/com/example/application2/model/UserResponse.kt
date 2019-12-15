package com.example.application2.model

import com.google.gson.annotations.SerializedName

data class UserResponse (@SerializedName("valido") var respuesta:String,
                         @SerializedName("usuario") var user: UserModel)