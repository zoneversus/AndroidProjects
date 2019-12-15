package com.example.application2.Activity.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.application2.R
import com.example.application2.actividades.Menu
import com.example.application2.task.LoginTask
import com.example.application2.utils.SharedPreference
import com.example.application2.utils.Validation
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        tvRecuperar?.setOnClickListener {
            val intent = Intent(this,Recovery::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        tvRegistrarse?.setOnClickListener {
            val intent = Intent(this,Register::class.java)
            startActivity(intent)

        }
        btnIngresar?.setOnClickListener {
           if (!etPass.text.isEmpty()&&!etUsuario.text.isEmpty()){
               if (Validation.isEmailValid(etUsuario.text.toString())){
                   if (Validation.verifyAvailableNetwork(this)){
                       login()
                   }else{
                       Toast.makeText(this, "Verifica tu conexion a internet", Toast.LENGTH_LONG)
                           .show()
                   }
               }else{
                   Toast.makeText(this, "Debes ingresar un correo valido", Toast.LENGTH_LONG)
                       .show()
               }
           }else{
               Toast.makeText(this, "Debes ingresar un usuario y contraseña", Toast.LENGTH_LONG)
                   .show()
           }

        }
    }

    private fun login() {
        doAsync {
            val user = LoginTask.loginUser(getString(R.string.ws_url_login),etUsuario.text.toString(),etPass.text.toString())
            uiThread {
                if(user.respuesta == "1") {
                    Log.i("respuesta",user.toString())
                    SharedPreference(applicationContext).save("usuario",user.user)
                    SharedPreference(applicationContext).save("userLogged",user.user.nombre)
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
