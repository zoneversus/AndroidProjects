package com.example.tareas.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tareas.R
import com.example.tareas.adapter.NotificacionAdapter
import com.example.tareas.adapter.TareasAdapter
import com.example.tareas.databinding.ActivityNotificacionesBinding
import com.example.tareas.databinding.ActivityTareasBinding
import com.example.tareas.model.NotificacionModel
import com.example.tareas.model.TareasModel
import com.example.tareas.task.NotificacionTask
import com.example.tareas.task.TareasTask
import com.example.tareas.util.CodesServices
import com.example.tareas.util.SharedPreference
import com.example.tareas.util.Toolbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class Tareas : Toolbar() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var tareas: List<TareasModel>
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityTareasBinding>(
            this,
            R.layout.activity_tareas
        )
    }

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inicializarToolbar(binding.tool, 2, "Tareas")
        tareas = ArrayList()
        getValueWS()
    }

    @ExperimentalStdlibApi
    private fun getValueWS() {
        doAsync {
            val userid = SharedPreference(applicationContext).getValueString("userLogged")
            val word:String = CodesServices.LISTA_TAREAS+"|"+userid
            val encodeValue= com.example.tareas.util.Helper.stringTo64(word)
            val message =
                TareasTask.getTareas(getString(R.string.url_service),encodeValue)
            uiThread {
                linearLayoutManager = LinearLayoutManager(applicationContext)
                binding.rvTareas.layoutManager = linearLayoutManager
                binding.rvTareas.adapter = TareasAdapter( message.tareas)
            }
        }
    }
}