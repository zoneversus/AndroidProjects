package com.example.tareas.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tareas.databinding.ItemEncuestaAbiertaBinding
import com.example.tareas.model.EncuestaModel
import com.example.tareas.util.SharedPreference


class EncuestaAdapter(private val items: List<EncuestaModel>) : RecyclerView.Adapter<EncuestaAdapter.ViewHolder>() {
    private var contexto: Context? =null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        contexto=parent.context

        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemEncuestaAbiertaBinding.inflate(inflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = items.size

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        val userid = SharedPreference(contexto!!.applicationContext).getValueString("userLogged")
        holder.binding.preguntaNumero.setText("Pregunta "+(position+1))
        if (items[position].preg_tipo=="1"){
            holder.binding.abierta.visibility=View.VISIBLE

            holder.binding.abierta.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {

                    SharedPreference(contexto!!).save("abierta",items[position].preg_id+"@@@"+"0"+"@@@"+s.toString()+"@@@"+userid+"|")

                }
            })
        }else if (items[position].preg_tipo=="2"){
            holder.binding.stars.visibility=View.VISIBLE
            SharedPreference(contexto!!).save("stars",items[position].preg_id+"@@@"+"0"+"@@@"+"0"+"@@@"+userid+"|")
            holder.binding.rate.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                SharedPreference(contexto!!).save("stars",items[position].preg_id+"@@@"+"0"+"@@@"+rating.toInt().toString()+"@@@"+userid+"|")
            }
        }else if (items[position].preg_tipo=="3"){
            SharedPreference(contexto!!).save("YN", items[position].preg_id+"@@@"+"0"+"@@@"+"1"+"@@@"+userid)

            holder.binding.yn.visibility=View.VISIBLE
            holder.binding.rYes.setOnClickListener {
                SharedPreference(contexto!!).save("YN",items[position].preg_id+"@@@"+"0"+"@@@"+"1"+"@@@"+userid)
            }
            holder.binding.rNo.setOnClickListener {
                SharedPreference(contexto!!).save("YN",items[position].preg_id+"@@@"+"0"+"@@@"+"2"+"@@@"+userid)
            }
        }else{
            holder.binding.multiple.visibility=View.VISIBLE
            holder.binding.pregunta1.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    SharedPreference(contexto!!).save("pregunta1",items[position].preg_id+"@@@"+ items[position].ListaRespuestas!![0].RespuestaID+"@@@"+"0"+"@@@"+userid)
                }else{
                    SharedPreference(contexto!!).save("pregunta1","")
                }
            }
            holder.binding.pregunta2.setOnCheckedChangeListener { buttonView, isChecked ->
                if (  isChecked){
                    SharedPreference(contexto!!).save("pregunta2",items[position].preg_id+"@@@"+ items[position].ListaRespuestas!![1].RespuestaID+"@@@"+"0"+"@@@"+userid)
                }else{
                    SharedPreference(contexto!!).save("pregunta2","")
                }
            }
            holder.binding.pregunta3.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    SharedPreference(contexto!!).save("pregunta3",items[position].preg_id+"@@@"+ items[position].ListaRespuestas!![2].RespuestaID+"@@@"+"0"+"@@@"+userid)
                }else{
                    SharedPreference(contexto!!).save("pregunta3","")
                }
            }
            holder.binding.pregunta4.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    SharedPreference(contexto!!).save("pregunta4",items[position].preg_id+"@@@"+ items[position].ListaRespuestas!![3].RespuestaID+"@@@"+"0"+"@@@"+userid)
                }else{
                    SharedPreference(contexto!!).save("pregunta4","")
                }
            }
        }

    }

    inner class ViewHolder(val binding: ItemEncuestaAbiertaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(encuestaModel: EncuestaModel) {
            binding.elemento= encuestaModel
            binding.executePendingBindings()
        }
    }



}
