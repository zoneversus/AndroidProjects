package com.example.noticiascognitus.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.noticiascognitus.R
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
        val mFab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        mFab.setOnClickListener {
            var editor = sharedPreference.edit()
            editor.putString("usr_id","")
            editor.commit()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        noticiasLayout?.setOnClickListener {
            val intent = Intent(this,CasaActivity::class.java)
            startActivity(intent)
        }


    }
}
