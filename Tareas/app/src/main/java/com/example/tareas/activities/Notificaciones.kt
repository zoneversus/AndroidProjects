package com.example.tareas.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.solver.widgets.Helper
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tareas.R
import com.example.tareas.adapter.NotificacionAdapter
import com.example.tareas.databinding.ActivityNotificacionesBinding
import com.example.tareas.model.NotificacionModel
import com.example.tareas.task.NotificacionTask
import com.example.tareas.util.CodesServices
import com.example.tareas.util.SharedPreference
import com.example.tareas.util.Toolbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class Notificaciones : Toolbar() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var notificaciones:List<NotificacionModel>
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityNotificacionesBinding>(this, R.layout.activity_notificaciones) }

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inicializarToolbar(binding.tool, 2,"Notificaciones")
        notificaciones=ArrayList()
        getValueWS()
    }

    @ExperimentalStdlibApi
    private fun getValueWS() {
        doAsync {
            val userid = SharedPreference(applicationContext).getValueString("userLogged")
            val word:String =CodesServices.LISTA_NOTIFICACIONES+"|"+userid
            val encodeValue= com.example.tareas.util.Helper.stringTo64(word)
            val message =NotificacionTask.getNotificaciones(getString(R.string.url_service),encodeValue)
            uiThread {
                linearLayoutManager = LinearLayoutManager(applicationContext)
                binding.rvNoticias.layoutManager = linearLayoutManager
                binding.rvNoticias.adapter = NotificacionAdapter( message.notificacion)
            }
        }
    }

}
