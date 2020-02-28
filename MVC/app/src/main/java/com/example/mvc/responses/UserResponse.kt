package com.example.mvc.responses

import com.example.mvc.models.UserViewModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse(
    @SerializedName("valido") @Expose val valido:String,
    @SerializedName("usuario") @Expose val  user:UserViewModel)