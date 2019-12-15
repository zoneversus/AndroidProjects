package com.example.tareas.util

import android.content.Intent
import android.opengl.Visibility
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tareas.R
import com.example.tareas.activities.Login
import com.example.tareas.activities.Menu
import com.example.tareas.activities.Registro
import com.example.tareas.databinding.ToolbarBinding


abstract class Toolbar : AppCompatActivity() {
var tipo=2
    fun inicializarToolbar(toolbarBinding: ToolbarBinding, tipoBtn: Int, titulo:String) {
        setSupportActionBar(toolbarBinding.toolbar)
        tipo=tipoBtn
        if (tipo == 1) {
            toolbarBinding.ivBtn1.background = getDrawable(R.drawable.rectangulo)
            toolbarBinding.ivBtn1.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.invalid_name,
                0,
                0,
                0
            )
            toolbarBinding.ivBtn1.text = "Cerrar sesion"
            supportActionBar?.setHomeButtonEnabled(false)
        }
        if (tipo == 2) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.retorno)
            toolbarBinding.ivBtn1.visibility= View.GONE
            toolbarBinding.titulo.text=titulo
        }

        toolbarBinding.setClickListener {
                when (it!!.id) {
                    toolbarBinding.ivBtn1.id -> {
                        SharedPreference(applicationContext).save("userLogged","")
                        val intent = Intent(applicationContext, Login::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    }
                }
            }
     }
    override fun onSupportNavigateUp(): Boolean {
        if (tipo==2){
            onBackPressed()
        }else{

        }
        return true
    }
    }

