package com.example.perritosretrofit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perritosretrofit.R
import com.example.perritosretrofit.tool.fromUrl
import kotlinx.android.synthetic.main.item_dog.view.*

class DogAdapter (val images: List<String>) : RecyclerView.Adapter<DogAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = images[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_dog, parent, false))
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(image: String) {
            itemView.ivDog.fromUrl(image)
        }
    }
}
