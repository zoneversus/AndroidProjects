package com.example.application2.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.application2.Activity.activities.Login
import com.example.application2.R
import com.example.application2.utils.SharedPreference
import kotlinx.android.synthetic.main.activity_menu_activy.*

class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_activy)
        supportActionBar?.hide()
        llReportes?.setOnClickListener {
            val intent = Intent(this,Notes::class.java)
            startActivity(intent)
        }
        llSignature?.setOnClickListener {
            val intent = Intent(this,Signature::class.java)
            startActivity(intent)
        }
        llProducts?.setOnClickListener {
            val intent = Intent(this,ProductsCataloge::class.java)
            startActivity(intent)
        }
        llProfile?.setOnClickListener {
            val intent = Intent(this,UpdateProfile::class.java)
            startActivity(intent)

        }
        tvwUser.text= "Bienvenido "+SharedPreference(applicationContext).getValueString("userLogged")
        val mFab = findViewById<Button>(R.id.logout)
        mFab.setOnClickListener {
           SharedPreference(this).save("userLogged","")
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}
