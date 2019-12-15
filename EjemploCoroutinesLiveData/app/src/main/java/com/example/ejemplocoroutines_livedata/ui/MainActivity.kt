package com.example.ejemplocoroutines_livedata.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ejemplocoroutines_livedata.R
import com.example.ejemplocoroutines_livedata.data.Todo
import kotlinx.android.synthetic.main.activity_main.*

//https://developer.android.com/topic/libraries/architecture/livedata
//https://medium.com/@elye.project/understanding-live-data-made-simple-a820fcd7b4d0
//https://developer.android.com/kotlin/coroutines
//https://medium.com/androiddevelopers/coroutines-on-android-part-i-getting-the-background-3e0e54d20bb
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var coroutineViewModel: CoroutineMainViewModel
    //el observador simplemente recibirá el objeto a ser procesado por la vista
    private val observer = Observer<Todo> {
        titleTextView.text = it.titulo
        idTextView.text = it.id.toString()
        userIdTextView.text = it.idUsuario.toString()
        completedTextView.text = it.completado.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //se obtienen los objetos de livedata
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        coroutineViewModel = ViewModelProviders.of(this).get(CoroutineMainViewModel::class.java)

        getWithOnlyLiveData.setOnClickListener {
            viewModel.getFirstTodo().observe(this, observer)
        }
        getWithCoroutines.setOnClickListener {
            coroutineViewModel.getFirstTodo().observe(this, observer)
        }
    }

}