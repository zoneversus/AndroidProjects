package com.example.application2.model

import com.google.gson.annotations.SerializedName

class ProductsResponse (@SerializedName("productos") var products: ArrayList<ProductoModel>)