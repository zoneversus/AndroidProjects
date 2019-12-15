package com.example.tareas.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tareas.R
import com.example.tareas.adapter.EncuestaAdapter
import com.example.tareas.databinding.ActivityEncuestaBinding
import com.example.tareas.model.EncuestaModel
import com.example.tareas.model.NotificacionModel
import com.example.tareas.task.EncuestaTask
import com.example.tareas.util.CodesServices
import com.example.tareas.util.SharedPreference
import com.example.tareas.util.Toolbar
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class Encuesta : Toolbar() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var encuestas:List<EncuestaModel>
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityEncuestaBinding>(this, R.layout.activity_encuesta) }

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inicializarToolbar(binding.tool,2,"Encuesta")
        encuestas = ArrayList()
        getValueWS()
        binding.setClickListener {
            when (it!!.id) {
                binding.send.id -> {
                    if(SharedPreference(applicationContext).getValueString("pregunta1")==""
                        &&SharedPreference(applicationContext).getValueString("pregunta2")==""
                        &&SharedPreference(applicationContext).getValueString("pregunta3")==""&&
                        SharedPreference(applicationContext).getValueString("pregunta4")==""){
                        Toast.makeText(this,"Debes de seleccionar una opcion en la pregunta 4",Toast.LENGTH_LONG).show()
                    }else{
                        sendEncuesta()
                    }

                }
            }
        }
    }
    @ExperimentalStdlibApi
    private fun getValueWS() {
        doAsync {
            val userid = SharedPreference(applicationContext).getValueString("userLogged")
            val word:String = CodesServices.PREGUNTAS+"|"+userid
            val encodeValue= com.example.tareas.util.Helper.stringTo64(word)
            val message =
                EncuestaTask.getEncuesta(getString(R.string.url_service),encodeValue)
            uiThread {
                linearLayoutManager = LinearLayoutManager(applicationContext)
                encuestas=message.tareas
                binding.rvEncuesta.layoutManager = linearLayoutManager
                binding.rvEncuesta.adapter = EncuestaAdapter( message.tareas)
            }
        }
    }
    @ExperimentalStdlibApi
    private fun sendEncuesta() {
        doAsync {
            val word:String = getQuery()
            val encodeValue= com.example.tareas.util.Helper.stringTo64(word)
            val message =
                EncuestaTask.updateEncuesta(getString(R.string.url_service),encodeValue)
            uiThread {
                if(message.valido == "1") {
                    Log.i("respuesta",message.toString())
                    Toast.makeText(applicationContext,"Encuesta enviada",Toast.LENGTH_LONG).show()
                    clearValues()
                    val intent = Intent(applicationContext,Menu::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    showErrorDialog()
                }
            }
        }
    }
    private fun clearValues(){
        SharedPreference(applicationContext).save("stars","")
        SharedPreference(applicationContext).save("abierta","")
        SharedPreference(applicationContext).save("YN","")
        SharedPreference(applicationContext).save("pregunta1","")
        SharedPreference(applicationContext).save("pregunta2","")
        SharedPreference(applicationContext).save("pregunta3","")
        SharedPreference(applicationContext).save("pregunta4","")
    }

    private fun getQuery():String{
        val valorEncuestaAbierta = SharedPreference(applicationContext).getValueString("abierta")
        val valorEncuestaYN = SharedPreference(applicationContext).getValueString("YN")
        val valorEncuestaStars= SharedPreference(applicationContext).getValueString("stars")
        val valorEncuestaMultiple1=SharedPreference(applicationContext).getValueString("pregunta1")
        val valorEncuestaMultiple2=SharedPreference(applicationContext).getValueString("pregunta2")
        val valorEncuestaMultiple3=SharedPreference(applicationContext).getValueString("pregunta3")
        val valorEncuestaMultiple4=SharedPreference(applicationContext).getValueString("pregunta4")

        var query=CodesServices.ENVIAR+"|"+ valorEncuestaAbierta+
                valorEncuestaStars+valorEncuestaYN

        if (valorEncuestaMultiple1!=""){
            query=query+"|"+valorEncuestaMultiple1
        }
        if (valorEncuestaMultiple2!=""){
            query=query+"|"+valorEncuestaMultiple2
        }
        if (valorEncuestaMultiple3!=""){
            query=query+"|"+valorEncuestaMultiple3
        }
        if (valorEncuestaMultiple4!=""){
            query=query+"|"+valorEncuestaMultiple4
        }
        return query
    }
    private fun showErrorDialog() {
        alert("Ha ocurrido un error, intentelo de nuevo.") {
            yesButton {dismiss() }
        }.show()
    }
}
