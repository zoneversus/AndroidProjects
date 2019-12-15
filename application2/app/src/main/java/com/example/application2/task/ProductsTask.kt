package com.example.application2.task

import com.example.application2.interfaz.APIService
import com.example.application2.model.MessageResponse
import com.example.application2.model.ProductsResponse

class ProductsTask {
    companion object{
        fun getProducts(url: String): ProductsResponse {
            val call =
                RetrofitBuilder.getRetrofit(url).create(APIService::class.java).getProducts().execute()
            val message = call.body() as ProductsResponse
            return message
        }
    }
}