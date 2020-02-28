package com.example.dagger

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("valido") val valido:Int,
    @SerializedName("usuario") val usuario:UserModel
)