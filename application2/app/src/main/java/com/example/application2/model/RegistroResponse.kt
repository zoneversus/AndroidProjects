package com.example.application2.model

import com.google.gson.annotations.SerializedName

class RegistroResponse (@SerializedName("valido") var valido:String,
                        @SerializedName("mensaje") var mensaje:String,
                        @SerializedName("registro") var user: UserModel)