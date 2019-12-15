package com.example.ejemplocoroutines_livedata.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//http://www.jsonschema2pojo.org/
data class Todo(@SerializedName("userId") @Expose val idUsuario: Int = 0,
                @SerializedName("id") @Expose val id: Int = 0,
                @SerializedName("title") @Expose val titulo: String = "",
                @SerializedName("completed") @Expose val completado: Boolean = false)