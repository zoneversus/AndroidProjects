package com.example.cognitusnews.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cognitusnews.R
import com.example.cognitusnews.activities.NoticiaDetalle
import com.example.cognitusnews.activities.Noticias
import com.example.cognitusnews.activities.WebViewActivity
import com.example.cognitusnews.model.NoticiaModel
import kotlinx.android.synthetic.main.card_noticia.view.*


class NoticiasAdapter(private val context: Noticias, private val chaptersList: ArrayList<NoticiaModel>) :
    RecyclerView.Adapter<NoticiasAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_noticia, parent, false))
    }

    override fun getItemCount(): Int {
        return chaptersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noticiaModel=chaptersList.get(position)

        holder.tituloNoticia?.text = chaptersList.get(position).notTitulo
        holder.fechaNoticia?.text= chaptersList.get(position).notFecha
        val requestManager = Glide.with(context)
        val imageUri = noticiaModel.notImg
        val requestBuilder = requestManager.load(imageUri)
        requestBuilder.into(holder.fotoNoticia)
        holder.ivVerMas.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("news_url", ""+noticiaModel.notUrl)
            context.startActivity(intent)
        }
        holder.itemView.setOnClickListener {
            var intent= Intent(context,NoticiaDetalle::class.java)
            intent.putExtra("noticia",noticiaModel)
            context.startActivity(intent)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tituloNoticia = view.tvTituloNoticia!!
        val fotoNoticia = view.ivNoticiaFoto!!
        val ivVerMas = view.ivVerMas!!
        val fechaNoticia = view.tvFechaNoticia!!
    }
}
