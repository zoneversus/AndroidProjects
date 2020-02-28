package com.example.dagger

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class UserViewModel : ViewModel(){

    private lateinit var parentJob:Job
    private var count=0
    private lateinit var coroutineContext: CoroutineContext
    private lateinit var scope:CoroutineScope
    private val repository : UserTask = UserTask()
    val userLiveData = MutableLiveData<UserResponse>()

    fun  initMovies(){
        parentJob=Job()
        coroutineContext = parentJob + Dispatchers.Default
        scope = CoroutineScope(coroutineContext)

    }
     fun fetchMovies(){
        scope.launch {
            while (true) {
                delay(3000)
                val user = repository.getUser(RetrofitBuilder.apiService) as UserResponse
                user.usuario.edad = user.usuario.edad + count
                count++
                userLiveData.postValue(user)
            }
        }
    }


    fun cancelAllRequests() = coroutineContext.cancelChildren()
    fun cancelJob()=parentJob.cancel()


}