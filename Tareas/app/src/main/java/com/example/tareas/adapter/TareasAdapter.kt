package com.example.tareas.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tareas.R
import com.example.tareas.activities.DetalleTareas
import com.example.tareas.databinding.ItemNotificacionBinding
import com.example.tareas.databinding.ItemTareasBinding
import com.example.tareas.model.TareasModel


class TareasAdapter(private val items: List<TareasModel>) : RecyclerView.Adapter<TareasAdapter.ViewHolder>() {
    private var contexto: Context? =null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        contexto=parent.context
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTareasBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.binding.setClickListener {
            when (it!!.id) {
                holder.binding.item.id -> {
                val intent = Intent(contexto,DetalleTareas::class.java)
                    intent.putExtra("elemento",items[position])
                    contexto?.startActivity(intent)
                }
            }
        }
        if (items[position].usrTareaStatus=="1"){
            holder.binding.item.setBackgroundResource(R.drawable.group_17)
            holder.binding.tvstatus.setText("Urgente")
            holder.binding.tvstatus.setTextColor(contexto?.resources!!.getColor(R.color.colorRed))
        }else{
            holder.binding.item.setBackgroundResource(R.drawable.group_18)
            holder.binding.tvstatus.setText("Normal")
            holder.binding.tvstatus.setTextColor(contexto?.resources!!.getColor(R.color.colorGreen))
        }
    }

    inner class ViewHolder(val binding: ItemTareasBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tareaModel: TareasModel) {
            binding.elemento= tareaModel
            binding.executePendingBindings()
        }
    }
}
