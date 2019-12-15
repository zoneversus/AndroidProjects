package com.example.cognitusnews.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import com.example.cognitusnews.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_menu.*

class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        supportActionBar?.hide()
        val sharedPreference =  getSharedPreferences("mi_app_cognitus", Context.MODE_PRIVATE)
        val usrId = sharedPreference.getString("usr_name", "")
        tvwUser.setText("Bienvenido "+usrId)
        val mFab = findViewById<Button>(R.id.logout)
        mFab.setOnClickListener {
            var editor = sharedPreference.edit()
            editor.putString("usr_id","")
            editor.commit()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val noticias = findViewById<LinearLayout>(R.id.llNoticias)
        noticias.setOnClickListener {
            val intent = Intent(this, Noticias::class.java)
            startActivity(intent)
        }
        val productos = findViewById<LinearLayout>(R.id.llProductos)
        productos.setOnClickListener {
            val intent = Intent(this, Productos::class.java)
            startActivity(intent)
        }
        llAcercade?.setOnClickListener {
            val intent = Intent(this, AcercaDeActivity::class.java)
            startActivity(intent)
        }
        llContacto?.setOnClickListener {
            val intent = Intent(this, ContactoActivity::class.java)
            startActivity(intent)
        }
    }
}
