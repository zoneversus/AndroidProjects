package com.example.tareas.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.example.tareas.R
import com.example.tareas.databinding.ActivityLoginBinding
import com.example.tareas.task.UserTask
import com.example.tareas.util.Helper
import com.example.tareas.util.CodesServices
import com.example.tareas.util.SharedPreference
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class Login : AppCompatActivity(), Validator.ValidationListener {
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)
        binding.setClickListener {
            when (it!!.id) {
                binding.btnIniciar.id -> {
                    validator!!.toValidate()
                }binding.tvReguistrar.id ->{
                val intent = Intent(applicationContext,Registro::class.java)
                startActivity(intent)
                }binding.tvOlvide.id ->{
                val intent = Intent(applicationContext,Recuperar::class.java)
                startActivity(intent)
                }
            }
        }
    }

    override fun onValidationError() {

    }

    @ExperimentalStdlibApi
    override fun onValidationSuccess() {
        doLogin()
    }
    @ExperimentalStdlibApi
    private fun doLogin() {
        doAsync {
            val rawData = CodesServices.LOGIN+"|"+etUsuario.text.toString()+"|"+etPassword.text.toString()
            val dataToSend= Helper.stringTo64(rawData)
            val user = UserTask.loginUser(getString(R.string.url_service),dataToSend)
            uiThread {
                if(user.valido == "1") {
                    Log.i("respuesta",user.toString())
                    SharedPreference(applicationContext).save("usuario",user.user)
                    SharedPreference(applicationContext).save("userLogged",user.user.usr_id)
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
