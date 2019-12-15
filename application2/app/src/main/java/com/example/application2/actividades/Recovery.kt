package com.example.application2.Activity.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.application2.R
import com.example.application2.actividades.Menu
import com.example.application2.task.LoginTask
import com.example.application2.task.RecoveryTask
import com.example.application2.utils.SharedPreference
import com.example.application2.utils.Validation
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_recovery.*
import kotlinx.android.synthetic.main.activity_recovery.tvRegistrarse
import okhttp3.internal.Util
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class Recovery : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery)
        supportActionBar?.hide()
        tvIniciar?.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
        btnRecovery?.setOnClickListener {
            if (Validation.isEmailValid(etRecovery.text.toString())){
                if (Validation.verifyAvailableNetwork(this)){
                    doRecovery()
                }
            }
        }
        tvRegistrarse?.setOnClickListener {
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun doRecovery() {
        doAsync {
            val user = RecoveryTask.recoveryUser(getString(R.string.ws_url_login),etRecovery.text.toString())
            uiThread {
                if(user.valido == "1") {
                    Log.i("respuesta",user.toString())
                    Toast.makeText(applicationContext,user.mensaje+"",Toast.LENGTH_LONG).show()
                    val intent = Intent(applicationContext, Login::class.java)
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
        alert("Ha ocurrido un error, intantelo de nuevo.") {
            yesButton {dismiss() }
        }.show()
    }
}
