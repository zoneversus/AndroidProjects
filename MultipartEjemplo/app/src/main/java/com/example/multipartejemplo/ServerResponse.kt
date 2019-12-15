package com.example.multipartejemplo

import com.google.gson.annotations.SerializedName


class ServerResponse(
    // variable name should be same as in the json response
    @SerializedName("mensaje") var mensaje: String? = null,
    @SerializedName("valido") var valido: String? = null,
    @SerializedName("registro") var user: UserModel)
