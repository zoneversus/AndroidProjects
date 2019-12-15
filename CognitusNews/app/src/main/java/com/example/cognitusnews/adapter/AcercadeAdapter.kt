package com.example.cognitusnews.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cognitusnews.R
import com.example.cognitusnews.activities.AcercaDeActivity
import com.example.cognitusnews.model.AcercadeModel
import kotlinx.android.synthetic.main.card_acercade.view.*

class AcercadeAdapter(private val context: AcercaDeActivity, private val chaptersList: ArrayList<AcercadeModel>) :
    RecyclerView.Adapter<AcercadeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_acercade, parent, false))
    }

    override fun getItemCount(): Int {
        return chaptersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noticiaModel=chaptersList.get(position)
        Log.i("Nosotros",""+chaptersList.get(position).nosotrosDesc)
        holder.descAcercade?.text = chaptersList.get(position).nosotrosDesc
        val requestManager = Glide.with(context)
        val imageUri = noticiaModel.nosotrosImg
        val requestBuilder = requestManager.load(imageUri)
        requestBuilder.into(holder.fotoAcerca)


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val descAcercade = view.tvDescAcercade!!
        val fotoAcerca = view.ivAcercaFoto!!
    }
}
