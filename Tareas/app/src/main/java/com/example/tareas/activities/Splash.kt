package com.example.tareas.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.tareas.R
import com.example.tareas.util.SharedPreference

class Splash : AppCompatActivity(),
    Runnable {
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        simularCarga()
    }
    private fun simularCarga() {
        handler = Handler()
        handler.postDelayed(this, 3000)
    }

    //simulación de carga
    override fun run() {

       val sharedPreference = SharedPreference(this)

        val usr = sharedPreference.getValueString("userLogged")

       if (usr.equals("")||usr.equals(null)) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
            finish()
        }
    }
}