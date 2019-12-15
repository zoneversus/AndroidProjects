package com.example.application2.task

import com.example.application2.interfaz.APIService
import com.example.application2.model.PedidoResponse

class PedidoTask {
    companion object{
        fun doPedido(url: String,id:String): PedidoResponse {
            val call =
                RetrofitBuilder.getRetrofit(url).create(APIService::class.java).purchase(id).execute()
            val message = call.body() as PedidoResponse
            return message
        }
    }
}