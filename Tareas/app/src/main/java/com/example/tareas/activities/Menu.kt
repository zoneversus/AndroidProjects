package com.example.tareas.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.tareas.R
import com.example.tareas.util.Toolbar
import com.example.tareas.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class Menu : Toolbar() {
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       inicializarToolbar(binding.barraP,1,"")
       binding.btnCheck.setOnClickListener {
           val intent = Intent(applicationContext,CheckIn::class.java)
           startActivity(intent)
       }
        binding.btnPerfil.setOnClickListener {
            val intent = Intent(applicationContext,Perfil::class.java)
            startActivity(intent)
        }
        binding.btnNotificaciones.setOnClickListener {
            val intent = Intent(applicationContext,Notificaciones::class.java)
            startActivity(intent)
        }
        binding.btnTareas.setOnClickListener {
            val intent = Intent(applicationContext,Tareas::class.java)
            startActivity(intent)
        }
        binding.btnEncuesta.setOnClickListener {
            val intent = Intent(applicationContext,Encuesta::class.java)
            startActivity(intent)
        }
    }
}
