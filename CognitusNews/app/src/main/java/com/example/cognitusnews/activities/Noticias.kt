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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cognitusnews.R
import com.example.cognitusnews.adapter.NoticiasAdapter
import com.example.cognitusnews.model.NoticiaModel
import com.example.cognitusnews.tasks.ApiGetPostHelper
import com.example.cognitusnews.utilities.Utils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class Noticias : AppCompatActivity() {
    lateinit var pBar: ProgressBar
    lateinit var tvMsg: TextView
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)
        supportActionBar?.hide()
        setTitle("Noticias")
        pBar = findViewById(R.id.pBar)
        tvMsg = findViewById(R.id.tvMsg)
        linearLayoutManager = LinearLayoutManager(this)
        if (Utils.verifyAvailableNetwork(this)) {
            // Call AsyncTask for getting list from server in JSON format
            getNoicias().execute()
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
    inner class getNoicias : AsyncTask<Void, Void, String>() {

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
            return ApiGetPostHelper.SendParams(getString(R.string.ws_url), sendParams)
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
                val listaNoticias = ArrayList<NoticiaModel>()
                // create JSONObject from string response
                val rootJsonObject = JSONObject(results)
//                val isSucess = rootJsonObject.getString("noticias")
                val noticiasObject = rootJsonObject.getString("noticias")
                val noticiasArray = JSONArray(noticiasObject)
                for (i in 0 until noticiasArray.length()) {
                    // Get single JSON object node
                    val sObject = noticiasArray.get(i).toString()
                    val mItemObject = JSONObject(sObject)
                    // Get String value from json object
                    val notId = mItemObject.getString("not_id")
                    val notImg = getString(R.string.img_url) + mItemObject.getString("not_img")
                    val notUrl = mItemObject.getString("not_url")
                    val notTitulo = mItemObject.getString("not_titulo")
                    val notFecha = mItemObject.getString("not_fecha")
                    val notDesc = mItemObject.getString("not_desc")
                    val objeto = NoticiaModel(notId, notImg, notUrl, notTitulo, notFecha, notDesc)
                    listaNoticias.add(objeto)
                }

                linearLayoutManager = LinearLayoutManager(applicationContext)
                val rvNoticias = findViewById<RecyclerView>(R.id.rvNoticias)
                rvNoticias.layoutManager = linearLayoutManager
                rvNoticias.adapter = NoticiasAdapter(this@Noticias, listaNoticias)
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
