package com.example.corutineslivedatalifecycle

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {
    val handler = CoroutineExceptionHandler { context, throwable ->
        Log.d("LifecycleScope", "$throwable")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launchWhenStarted{
            launch(handler) {
                   var value = 0
                   while (true){
                       delay(1000)
                       Log.d("LifecycleScope", "From corutine 1: ${++value}")
                   }
            }
            launch(handler) {
                var value = 0
                while (true){
                    delay(1000)
                    Log.d("LifecycleScope", "From corutine 2: ${++value}")
                }
            }
            //Multible async task that's to te end you depend that everyone its finish
            val returned = async(Dispatchers.IO){

            }.await()
            //Single task
            withContext(Dispatchers.Main){

            }
        }

        GlobalScope.launch(Dispatchers.Unconfined) {
            suspendCoroutine<Unit> {

            }
        }
        tostada("desu")

    }

    fun Context.tostada(message:CharSequence,duration: Int =Toast.LENGTH_SHORT){
        Toast.makeText(this,message,duration).show()
    }
}