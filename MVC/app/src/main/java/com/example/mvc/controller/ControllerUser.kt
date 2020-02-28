package com.example.mvc.controller

import com.example.mvc.models.UserViewModel
import com.example.mvc.services.Task

class ControllerUser{
    var task:Task

    init {
        task=Task()
    }

     fun getUser():UserViewModel{
        return task.getUser()
    }
}