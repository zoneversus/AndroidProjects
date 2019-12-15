package com.example.application2.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application2.R
import com.example.application2.adapter.ProductsAdapter
import com.example.application2.model.ProductoModel
import com.example.application2.task.LoginTask
import com.example.application2.task.ProductsTask
import com.example.application2.utils.SharedPreference
import com.example.application2.utils.Validation
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_products_detail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ProductsCataloge : AppCompatActivity() {
    lateinit var tvMsg: TextView
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var products:List<ProductoModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_cataloge)
        supportActionBar?.hide()
        setTitle("Noticias")
        products=ArrayList()


        tvMsg = findViewById(R.id.tvMsg)
        linearLayoutManager = LinearLayoutManager(this)
        if (Validation.verifyAvailableNetwork(this)) {
            // Call AsyncTask for getting list from server in JSON format
            getNoticias()
        } else {
            Toast.makeText(
                applicationContext,
                "Â¡No cuenta con conexiÃ³n a Internet!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun getNoticias() {
        doAsync {

            val products = ProductsTask.getProducts(getString(R.string.ws_url_login)).products
            uiThread {
                if(products.size>0) {
                    linearLayoutManager = LinearLayoutManager(applicationContext)
                    val rvNoticias = findViewById<RecyclerView>(R.id.rvNoticias)
                    rvNoticias.layoutManager = linearLayoutManager
                    rvNoticias.adapter = ProductsAdapter(this@ProductsCataloge, products)
                }else{
                    showErrorDialog()
                }
            }
        }
    }
    private fun showErrorDialog() {
        alert("Ha ocurrido un error, intÃ©ntelo de nuevo.") {
            yesButton { }
        }.show()
    }

}
