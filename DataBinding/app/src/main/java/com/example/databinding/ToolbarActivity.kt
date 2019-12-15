package com.example.databinding

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.databinding.databinding.ToolbarBinding

abstract class ToolbarActivity : AppCompatActivity() {

    fun inicializarToolbar(toolbarBinding: ToolbarBinding, titulo: String = "", tipoBtn: Int){
        setSupportActionBar(toolbarBinding.toolbar)
        toolbarBinding.titulo = titulo
        if(tipoBtn==1)
            toolbarBinding.ivBtn1.setImageResource(R.drawable.ico_usuarios)

        toolbarBinding.setClickListener {
            when (it!!.id) {
                toolbarBinding.ivBtn1.id -> {
                    Toast.makeText(this,"BotÃ³n barra ", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
