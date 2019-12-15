package com.example.cognitusnews.activities

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cognitusnews.R
import com.example.cognitusnews.adapter.NoticiasAdapter
import com.example.cognitusnews.adapter.ProductosAdapter
import com.example.cognitusnews.model.NoticiaModel
import com.example.cognitusnews.model.ProductoModel
import com.example.cognitusnews.tasks.ApiGetPostHelper
import com.example.cognitusnews.utilities.Utils
import kotlinx.android.synthetic.main.activity_productos.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class Productos : AppCompatActivity() {
    lateinit var pBar: ProgressBar
    lateinit var tvMsg: TextView
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)
        supportActionBar?.hide()
        setTitle("Productos")
        pBar = findViewById(R.id.pBarProd)
        tvMsg = findViewById(R.id.tvMsg)
        linearLayoutManager = LinearLayoutManager(this)
        if (Utils.verifyAvailableNetwork(this)) {
            // Call AsyncTask for getting list from server in JSON format
            getProductos().execute()
        } else {
            Toast.makeText(
                applicationContext,
                "Â¡No cuenta con conexiÃ³n a Internet!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Inner class Async to consume WS
    @SuppressLint("StaticFieldLeak")
    inner class getProductos : AsyncTask<Void, Void, String>() {

        override fun onPreExecute() {
            // show progressbar for UI experience
            pBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg voids: Void): String {
            // Here is post Json api example
            val sendParams = HashMap<String, String>()
            // Send parameters and value on JSON api
            sendParams["Name"] = ""
            // send the HttpPostRequest by HttpURLConnection and receive a Results in return string
            return ApiGetPostHelper.SendParams(getString(R.string.ws_url_prod), sendParams)
        }

        override fun onPostExecute(results: String?) {
            // Hide Progressbar
            pBar.visibility = View.GONE
            tvMsg.visibility = View.VISIBLE

            if (results != null) {
                // See Response in Logcat for understand JSON Results
                Log.i("Resultado: ", results)
            }

            try {
                val listaProductos = ArrayList<ProductoModel>()
                // create JSONObject from string response
                val rootJsonObject = JSONObject(results)
//                val isSucess = rootJsonObject.getString("noticias")
                val productosObject = rootJsonObject.getString("productos")
                val productosArray = JSONArray(productosObject)
                for (i in 0 until productosArray.length()) {
                    // Get single JSON object node
                    val sObject = productosArray.get(i).toString()
                    val mItemObject = JSONObject(sObject)
                    // Get String value from json object
                    val prodDesc = mItemObject.getString("prod_desc")
                    val prodTitulo = mItemObject.getString("prod_titulo")
                    val prodPrecio = mItemObject.getString("prod_precio")
                    val prodImg = getString(R.string.img_url) + mItemObject.getString("prod_img")
                    val prodId = mItemObject.getInt("prod_id")
                    val objeto = ProductoModel(prodDesc, prodTitulo, prodPrecio, prodImg, prodId)
                    listaProductos.add(objeto)
                }

                linearLayoutManager = LinearLayoutManager(applicationContext)
                val rvNoticias = findViewById<RecyclerView>(R.id.rvNoticias)
                rvNoticias.layoutManager = linearLayoutManager
                rvNoticias.adapter = ProductosAdapter(this@Productos, listaProductos)
            } catch (e: JSONException) {
                Toast.makeText(
                    applicationContext,
                    "Lo sentimos, algo salio mal. ¡Intenta de nuevo!",
                    Toast.LENGTH_SHORT
                ).show()
                e.printStackTrace()
            }
        }
    }

}
