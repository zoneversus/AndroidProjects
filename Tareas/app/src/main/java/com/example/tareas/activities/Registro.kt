package com.example.tareas.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.example.tareas.R
import com.example.tareas.util.Toolbar
import com.example.tareas.databinding.ActivityRegistroBinding
import com.example.tareas.task.UserTask
import com.example.tareas.util.Helper
import com.example.tareas.util.CodesServices
import com.example.tareas.util.SharedPreference
import kotlinx.android.synthetic.main.activity_registro.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class Registro : Toolbar(), Validator.ValidationListener {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityRegistroBinding>(this, R.layout.activity_registro)
    }

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inicializarToolbar(binding.tool, 2,"Registro")
        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)
        binding.setClickListener {
            when (it!!.id) {
                binding.btnIniciar.id -> {
                    validator!!.toValidate()
                }binding.tvTerminos.id ->{
                    doTerms()
                }
            }
        }

    }

    override fun onValidationError() {

    }

     @ExperimentalStdlibApi
     override fun onValidationSuccess() {
        if (binding.etPassword.text.toString() == binding.etPasswordConfirm.text.toString()){
            if (binding.cbAcepto.isChecked){
            doRegister()
            }else{
                Toast.makeText(this,"Por favor acepte los terminos y condiciones para continuar",Toast.LENGTH_LONG).show()
            }
        }else{
            alert("Las contraseñas no coiciden.") {
                yesButton {dismiss() }
            }.show()
        }
    }
    @ExperimentalStdlibApi
    private fun doTerms(){
        doAsync {
            val rawData = CodesServices.TYC
            val dataToSend= Helper.stringTo64(rawData)
            val user = UserTask.tycUser(getString(R.string.url_service),dataToSend)
            uiThread {
                if(user.valido == "1") {
                    Log.i("respuesta",user.toString())
                    val intent = Intent(applicationContext, WebViewActivity::class.java)
                    val url=getString(R.string.url_service)+user.url
                    intent.putExtra("news_url", url)
                    applicationContext.startActivity(intent)
                }else{
                    showErrorDialog()
                }
            }
        }
    }
    @ExperimentalStdlibApi
    private fun doRegister() {
        doAsync {
            val rawData = CodesServices.REGISTRO+"|"+etNombre.text.toString()+"|"+etCorreo.text.toString()+"|"+etPassword.text.toString()
            val dataToSend= Helper.stringTo64(rawData)
            val user = UserTask.registerUser(getString(R.string.url_service),dataToSend)
            uiThread {
                if(user.valido == "1") {
                    Log.i("respuesta",user.toString())
                    SharedPreference(applicationContext).save("usuario",user.user)
                     SharedPreference(applicationContext).save("userLogged",user.user.usu_id)
                    val intent = Intent(applicationContext,Menu::class.java)
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