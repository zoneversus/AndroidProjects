package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.crashlytics.android.Crashlytics
import com.example.firebase.databinding.ActivityMainBinding
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        supportActionBar?.hide()
        EventBus.getDefault().register(this)

        Crashlytics.log("Prueba paso algo")

        binding.btnTest.setOnClickListener {
            val intent = Intent(this,DemoCrash::class.java)
            startActivity(intent)
        }

    }

    @Subscribe
    fun enNotificacionRecibida(event: Notificacion){
        binding.titulo = event.titulo
        binding.mensaje = event.mensaje
        binding.datos = Gson().toJson(event.datos)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}
