package com.example.mvc.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvc.models.UserViewModel
import com.example.mvc.services.Task
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class UserViewHolder :ViewModel(){
    val user: MutableLiveData<UserViewModel> by lazy {
        MutableLiveData<UserViewModel>()
    }

}