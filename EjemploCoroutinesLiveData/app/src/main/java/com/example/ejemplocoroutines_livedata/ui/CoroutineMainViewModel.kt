package com.example.ejemplocoroutines_livedata.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.ejemplocoroutines_livedata.domain.TodoCoroutineRepository
import kotlinx.coroutines.Dispatchers


class CoroutineMainViewModel : ViewModel() {
    //si se quiere obtener mensajes de errores se tiene que crear un método, valor o similar desde
    //aquí que devuelvan o sean del tipo LiveData<> o MutableLiveData para que al cambiar se refleje
    //en la UI, es decir, los errores también deben ser tratados como objetos observables
    private val repository: TodoCoroutineRepository = TodoCoroutineRepository()

    fun getFirstTodo() = liveData(Dispatchers.IO) {
        emit(repository.getTodo(2))
    }
}