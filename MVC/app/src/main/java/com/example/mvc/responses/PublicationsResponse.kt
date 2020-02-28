package com.example.mvc.responses

import androidx.paging.PagedList
import com.example.mvc.models.PublicacionViewModel
import com.google.gson.annotations.SerializedName

data class PublicationsResponse(
  @SerializedName("valido") val valido:Int,
  @SerializedName("publicaciones")val publicaciones:List<PublicacionViewModel>
)