package com.example.tareas.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tareas.R
import com.example.tareas.activities.Menu
import com.example.tareas.databinding.ItemNotificacionBinding
import com.example.tareas.model.NotificacionModel
import com.example.tareas.task.NotificacionTask
import com.example.tareas.task.UserTask
import com.example.tareas.util.CodesServices
import com.example.tareas.util.Helper
import com.example.tareas.util.SharedPreference
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class NotificacionAdapter(private val items: List<NotificacionModel>) : RecyclerView.Adapter<NotificacionAdapter.ViewHolder>() {
    private var contexto: Context? =null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        contexto=parent.context
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNotificacionBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.binding.setClickListener {
            when (it!!.id) {
                holder.binding.Item.id -> {
                    if ((items[position].notStatus=="1")){
                      doUpdateNotification(items[position].notID!!,holder)
                    }
                }
            }
        }
        if (items[position].notStatus=="1"){
            holder.binding.ivStatus.setImageDrawable(contexto?.resources?.getDrawable(R.drawable.ellipse_2,contexto?.theme))
        }else{
            holder.binding.ivStatus.setImageDrawable(contexto?.resources?.getDrawable(R.drawable.ellipse_1,contexto?.theme))
        }

    }
    @ExperimentalStdlibApi
    private fun doUpdateNotification(idNotificacion:String,holder: ViewHolder) {
        doAsync {
            val userid =SharedPreference(contexto!!).getValueString("userLogged")
            val rawData = CodesServices.NOTIFICACION_LEIDA+"|"+idNotificacion+"|"+userid
            val dataToSend= Helper.stringTo64(rawData)
            val user = NotificacionTask.updateNotificacion(contexto!!.getString(R.string.url_service),dataToSend)
            uiThread {
                if(user.valido == "1") {
                    Log.i("respuesta",user.toString())
                    Toast.makeText(contexto!!,"Notificacion leida",Toast.LENGTH_LONG).show()
                    holder.binding.ivStatus.setImageDrawable(contexto?.resources?.getDrawable(R.drawable.ellipse_1,contexto?.theme))
                    //SharedPreference(applicationContext).save("usuario",user.user)
                    //  SharedPreference(applicationContext).save("userLogged",user.user.usr_id)
                }else{
                    Toast.makeText(contexto!!,"Hubo un error",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    inner class ViewHolder(val binding: ItemNotificacionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notificationModel: NotificacionModel) {
            binding.elemento= notificationModel
            binding.executePendingBindings()
        }
    }
}

