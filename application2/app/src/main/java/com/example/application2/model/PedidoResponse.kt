package com.example.application2.model

import com.google.gson.annotations.SerializedName

class PedidoResponse (@SerializedName("valido") var valido:String,
                      @SerializedName("respuesta") var respuesta: PedidoModel)