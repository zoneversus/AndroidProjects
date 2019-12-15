package com.example.noticiascognitus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.noticiascognitus.R
import com.example.noticiascognitus.activities.CasaActivity
import kotlinx.android.synthetic.main.item_casa.view.*

class CasaAdapter (private val context: CasaActivity, private val chaptersList: ArrayList<String>) :
    RecyclerView.Adapter<CasaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_casa, parent, false))
    }

    override fun getItemCount(): Int {
        return chaptersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tituloProd?.text = chaptersList.get(position)

        if(position==0)
            holder.fotoCasa?.setImageResource(R.drawable.casa_img)
        if(position==1)
            holder.fotoCasa?.setImageResource(R.drawable.casa_img2)
        if(position==2)
            holder.fotoCasa?.setImageResource(R.drawable.casa_img3)

        holder.itemView.setOnClickListener {
            Toast.makeText(context, chaptersList.get(position), Toast.LENGTH_LONG).show()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tituloProd = view.tvTituloProd!!
        val fotoCasa = view.ivFotoNot!!
    }
}

