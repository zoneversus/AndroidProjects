package com.example.mvc.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.mvc.models.PublicacionViewModel

class PublicacionesViewHolder :ViewModel(){
    val publicaciones:MutableLiveData<List<PublicacionViewModel>> by lazy {
        MutableLiveData<List<PublicacionViewModel>>()
    }
}