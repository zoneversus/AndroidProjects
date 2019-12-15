package com.example.ejemplocoroutines_livedata.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.ejemplocoroutines_livedata.data.Todo
import com.example.ejemplocoroutines_livedata.domain.TodoRepository

class MainViewModel : ViewModel() {
    private val repository: TodoRepository = TodoRepository()

    fun getFirstTodo(): LiveData<Todo> { return repository.getTodo(1) }
}