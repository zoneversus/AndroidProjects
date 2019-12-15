package com.example.application2.model

import com.google.gson.annotations.SerializedName

data class MessageResponse(@SerializedName("valido") var valido:String,
                           @SerializedName("mesaje") var mensaje: String)