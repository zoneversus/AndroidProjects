package com.example.dagger

import com.google.gson.annotations.SerializedName

data class UserModel (
    @SerializedName("user")val user: String?,
    @SerializedName("pass")val pass: String?,
    @SerializedName("edad")var edad: Int
)