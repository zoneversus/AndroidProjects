package com.example.mvc.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserViewModel (
    @SerializedName("user") @Expose val user:String,
    @SerializedName("pass") @Expose val  password:String,
    @SerializedName("edad") @Expose val  edad:Int)