package com.example.mvc.controller

import com.example.mvc.models.PublicacionViewModel
import com.example.mvc.models.UserViewModel
import com.example.mvc.services.Task
import com.example.mvc.views.PublicacionesViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ControllerPublicaciones {
    private var task: Task
    private val uiScope = CoroutineScope(Dispatchers.IO)
    private lateinit var publicacionesList: List<PublicacionViewModel>
    private var todoViewHolder: PublicacionesViewHolder

    init {
        task= Task()
        todoViewHolder = PublicacionesViewHolder()
    }

    fun getPublicaciones()=uiScope.launch {
        publicacionesList =  task.getPublicaciones()
        todoViewHolder.publicaciones.postValue(publicacionesList)
    }

    fun getViewHolderPublicaciones():PublicacionesViewHolder{
        return todoViewHolder
    }

    }
