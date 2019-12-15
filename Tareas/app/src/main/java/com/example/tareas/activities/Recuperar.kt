package com.example.tareas.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.example.tareas.R
import com.example.tareas.databinding.ActivityRecuperarBinding
import com.example.tareas.task.UserTask
import com.example.tareas.util.Helper
import com.example.tareas.util.CodesServices
import com.example.tareas.util.Toolbar
import kotlinx.android.synthetic.main.activity_recuperar.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class Recuperar :  Toolbar(), Validator.ValidationListener {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityRecuperarBinding>(this, R.layout.activity_recuperar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inicializarToolbar(binding.tool, 2,"Olvide contraseña")
        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)
        binding.setClickListener {
            when (it!!.id) {
                binding.btnIniciar.id -> {
                    validator!!.toValidate()
                }
            }
        }

    }

    override fun onValidationError() {

     }

    @ExperimentalStdlibApi
    override fun onValidationSuccess() {
        doRecovery()
      }
    @ExperimentalStdlibApi
    private fun doRecovery(){
        doAsync {
            val rawData = CodesServices.RECUPERAR+"|"+etCorreo.text.toString()
            val dataToSend= Helper.stringTo64(rawData)
            val user = UserTask.recoveryUser(getString(R.string.url_service),dataToSend)
            uiThread {
                if(user.valido == "1") {
                    Log.i("respuesta",user.toString())
                    Toast.makeText(applicationContext,"Se ha enviado un enlace al tu correo",Toast.LENGTH_LONG).show()
                    //  SharedPreference(applicationContext).save("usuario",user.user)
                    // SharedPreference(applicationContext).save("userLogged",user.user.nombre)
                    val intent = Intent(applicationContext,Login::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                }else{
                    showErrorDialog()
                }
            }
        }
    }
    private fun showErrorDialog() {
        alert("Ha ocurrido un error, intentelo de nuevo.") {
            yesButton {dismiss() }
        }.show()
    }
}

