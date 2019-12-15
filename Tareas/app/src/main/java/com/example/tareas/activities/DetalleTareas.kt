package com.example.tareas.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.example.tareas.R
import com.example.tareas.databinding.ActivityDetalleTareasBinding
import com.example.tareas.model.TareasModel
import com.example.tareas.task.TareasTask
import com.example.tareas.task.UserTask
import com.example.tareas.util.CodesServices
import com.example.tareas.util.Helper
import com.example.tareas.util.SharedPreference
import com.example.tareas.util.Toolbar
import kotlinx.android.synthetic.main.activity_detalle_tareas.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetalleTareas : Toolbar(),  Validator.ValidationListener{
    private lateinit var tarea: TareasModel
    private  var bandera = true
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityDetalleTareasBinding>(this, R.layout.activity_detalle_tareas)
    }
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inicializarToolbar(binding.barraP,2,"Detalle Tareas")
        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)
        tarea=intent.getParcelableExtra<TareasModel>("elemento")
        binding.elemento=tarea
        binding.executePendingBindings()

        if (tarea.usrTareaStatus=="1"){
            binding.circle.setBackgroundResource(R.drawable.circle_red)
        }else{
            binding.circle.setBackgroundResource(R.drawable.circle_green)
        }

        if (tarea.usrTareaFin=="0"){
            etHoras.text= Editable.Factory.getInstance().newEditable(tarea.usrTareaHoras)
            etNotas.text=Editable.Factory.getInstance().newEditable(tarea.usrTareaNota)
            btnGuardar.visibility=View.GONE
            btnTerminar.visibility= View.GONE
            etHoras.isEnabled=false
            etNotas.isEnabled=false }

        binding.setClickListener {
            when (it!!.id) {
                binding.btnGuardar.id -> {
                    bandera=true
                    validator.toValidate()
                }binding.btnTerminar.id ->{
                bandera=false
             validator.toValidate()
            }
            }
        }
    }


    @ExperimentalStdlibApi
    private fun doEnd(){
        doAsync {
            val userid = SharedPreference(applicationContext).getValueString("userLogged")
            val rawData = CodesServices.TERMINAR+"|"+tarea.usrTareaId+"|"+userid+"|"+etHoras.text.toString()+"|"+
                    etNotas.text.toString()
            val dataToSend= Helper.stringTo64(rawData)
            val user = TareasTask.endTarea(getString(R.string.url_service),dataToSend)
            uiThread {
                if(user.valido == "1") {
                    Log.i("respuesta",user.toString())
                    Toast.makeText(applicationContext,"Datos de la tarea actualizados",Toast.LENGTH_LONG).show()
                    val intent = Intent(applicationContext,Tareas::class.java)
                    startActivity(intent)
                    this@DetalleTareas.finish()
                }else{
                    showErrorDialog()
                }
            }
        }
    }
    @ExperimentalStdlibApi
    private fun doSave(){
        doAsync {
            val userid = SharedPreference(applicationContext).getValueString("userLogged")
            val rawData = CodesServices.GUARDAR_AVANCE+"|"+tarea.usrTareaId+"|"+userid+"|"+etHoras.text.toString()+"|"+
                    etNotas.text.toString()
            val dataToSend= Helper.stringTo64(rawData)
            val user = TareasTask.updateTareas(getString(R.string.url_service),dataToSend)
            uiThread {
                if(user.valido == "1") {
                    Log.i("respuesta",user.toString())
                    Toast.makeText(applicationContext,"Datos de la tarea actualizados",Toast.LENGTH_LONG).show()
                    val intent = Intent(applicationContext,Tareas::class.java)
                    startActivity(intent)
                    this@DetalleTareas.finish()
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

    override fun onValidationError() {

    }

    @ExperimentalStdlibApi
    override fun onValidationSuccess() {
        if (bandera){
            doSave()}
        else{
            doEnd()}
    }
}
