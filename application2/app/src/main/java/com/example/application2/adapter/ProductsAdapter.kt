package com.example.application2.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application2.R
import com.example.application2.actividades.ProductsCataloge
import com.example.application2.actividades.ProductsDetail
import com.example.application2.model.ProductoModel
import kotlinx.android.synthetic.main.item_product.view.*

class ProductsAdapter(private val context: ProductsCataloge, private val chaptersList: ArrayList<ProductoModel>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product, parent, false))
    }

    override fun getItemCount(): Int {
        return chaptersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productoModel=chaptersList.get(position)
        holder.tituloProducto?.text = chaptersList.get(position).title
        holder.prodDesc?.text= chaptersList.get(position).description
        holder.prodPrecio?.text="$ "+ chaptersList.get(position).price+" MXN"
        val requestManager = Glide.with(context)
        val imageUri ="http://35.155.161.8:8080/WSExample/"+ productoModel.image
        val requestBuilder = requestManager.load(imageUri)
        requestBuilder.into(holder.fotoProd)
        holder.itemView.setOnClickListener {
            var intent= Intent(context, ProductsDetail::class.java)
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
