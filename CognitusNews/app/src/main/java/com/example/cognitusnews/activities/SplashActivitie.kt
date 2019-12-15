package com.example.cognitusnews.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.cognitusnews.R

class SplashActivitie : AppCompatActivity(),
    Runnable {
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_activitie)
        supportActionBar?.hide()
        simularCarga()
    }

    private fun simularCarga() {
        handler = Handler()
        handler.postDelayed(this, 3000)
    }

    //simulación de carga
    override fun run() {
        val sharedPreference = getSharedPreferences(
            "mi_app_cognitus",
            Context.MODE_PRIVATE
        )
        val usrId = sharedPreference.getString("usr_id", "")
        Log.i("ValorDeUsrId", "->" + usrId)
        if (!usrId.equals("")) {
            val intent = Intent(this, Menu::class.java)
            intent.putExtra("nombre_usr", ""+usrId)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}