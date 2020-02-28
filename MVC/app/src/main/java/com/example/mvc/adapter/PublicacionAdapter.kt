package com.example.mvc.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devs.readmoreoption.ReadMoreOption
import com.example.mvc.R
import com.example.mvc.databinding.ItemPublicacionBinding
import com.example.mvc.models.PublicacionViewModel

class PublicacionAdapter (private val item: List<PublicacionViewModel>): RecyclerView.Adapter<PublicacionAdapter.ViewHolder> () {
    private lateinit var contexto: Context
    private val CONTENT_TYPE = 0
    private val AD_TYPE = 1

    inner class ViewHolder (val binding: ItemPublicacionBinding): RecyclerView.ViewHolder(binding.root){ // 2. debemos indicar donde se renderizara el modelo
        fun bind(publicacionModel: PublicacionViewModel){
            binding.elemento =publicacionModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {  // 1. primero debemos indicar donde inflaremos la vista
        contexto = parent.context
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPublicacionBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = item.size  // indicar que regrese el tamaño de la lista

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item[position])

        var readMoreOption
                = ReadMoreOption.Builder(contexto)
            .textLength(150, ReadMoreOption.TYPE_CHARACTER)
            .moreLabel("Mas")
            .lessLabel("Menos")
            .moreLabelColor(Color.RED)
            .lessLabelColor(Color.BLUE)
            .labelUnderLine(true)
            .expandAnimation(true)
            .build()
        readMoreOption.addReadMoreTo(holder.binding.descripcion, item[position].publicacionDesc)
        if (item[position].publicacionTipo==1){
            holder.binding.imagePublicacion.setImageResource(R.drawable.baseline_directions_bus_black_48)
            holder.binding.tipoCarga.text="Pasajeros"
        }else{
            holder.binding.imagePublicacion.setImageResource(R.drawable.baseline_local_shipping_black_48)
            holder.binding.tipoCarga.text="Carga"
        }

        if (item[position].publicacionPatrocinada==1){
            holder.binding.cardPublicacion.setCardBackgroundColor(Color.parseColor("#FAD2B4"))
        }else{
            holder.binding.cardPublicacion.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        }
        holder.binding.setClickListener {
            when(it!!.id){
                holder.binding.share.id->{
                    val shareIntent = Intent()
                    shareIntent.action = Intent.ACTION_SEND
                    shareIntent.type="text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                    contexto.startActivity(Intent.createChooser(shareIntent,"Compartir..."))
                }
                holder.binding.cardPublicacion.id ->{
               //val intent = Intent(holder.itemView.context, DetallePublicacionProActivity::class.java)
                 //intent.putExtra("publicacion_titulo", item[position].titulo_publicacion)
                   //intent.putExtra("publicacion_img", item[position].img_publicacion)
                   //intent.putExtra("publicacion_desc", item[position].description_publicacion)
                    //intent.putExtra("publicacion_fecha", item[position].date_publicacion)
                    //intent.putExtra("publicacion_user", item[position].user_publicacion)
                    //intent.putExtra("publicacion_contac", item[position].contact_publicacion)
                    //holder.itemView.context?.startActivity(intent)

                }


            }
        }
    }
}