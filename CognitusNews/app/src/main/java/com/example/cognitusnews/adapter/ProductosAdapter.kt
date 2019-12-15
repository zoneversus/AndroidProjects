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
import com.example.cognitusnews.activities.ProductoDetalle
import com.example.cognitusnews.activities.Productos
import com.example.cognitusnews.model.ProductoModel
import kotlinx.android.synthetic.main.card_noticia.view.ivVerMas
import kotlinx.android.synthetic.main.card_productos.view.*

class ProductosAdapter(private val context: Productos, private val chaptersList: ArrayList<ProductoModel>) :
    RecyclerView.Adapter<ProductosAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_productos, parent, false))
    }

    override fun getItemCount(): Int {
        return chaptersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productoModel=chaptersList.get(position)
        holder.tituloProducto?.text = chaptersList.get(position).prodTitle
        holder.prodDesc?.text= chaptersList.get(position).prodDesc
        holder.prodPrecio?.text="$ "+ chaptersList.get(position).prodPrice+" MXN"
        val requestManager = Glide.with(context)
        val imageUri = productoModel.prodImg
        val requestBuilder = requestManager.load(imageUri)
        requestBuilder.into(holder.fotoProd)
        holder.itemView.setOnClickListener {
            var intent= Intent(context, ProductoDetalle::class.java)
            intent.putExtra("producto",productoModel)
            context.startActivity(intent)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tituloProducto = view.tvTituloProd!!
        val fotoProd = view.ivProdFoto!!
        val prodDesc = view.tvDescProd!!
        val prodPrecio = view.tvPrice!!
    }
}
