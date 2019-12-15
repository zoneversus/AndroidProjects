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
import com.example.cognitusnews.adapter.AcercadeAdapter
import com.example.cognitusnews.adapter.NoticiasAdapter
import com.example.cognitusnews.model.AcercadeModel
import com.example.cognitusnews.model.NoticiaModel
import com.example.cognitusnews.tasks.ApiGetPostHelper
import com.example.cognitusnews.utilities.Utils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class AcercaDeActivity : AppCompatActivity() {
    lateinit var pBar: ProgressBar
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acerca_de)

        pBar = findViewById(R.id.pBar)
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Acerca de"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        if (Utils.verifyAvailableNetwork(this)) {
            // Call AsyncTask for getting list from server in JSON format
            getAcerca().execute()
        } else {
            Toast.makeText(
                applicationContext,
                "Â¡No cuenta con conexiÃ³n a Internet!",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // Inner class Async to consume WS
    @SuppressLint("StaticFieldLeak")
    inner class getAcerca : AsyncTask<Void, Void, String>() {

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
            return ApiGetPostHelper.SendParams(getString(R.string.ws_url_acerca), sendParams)
        }

        override fun onPostExecute(results: String?) {
            // Hide Progressbar
            pBar.visibility = View.GONE

            if (results != null) {
                // See Response in Logcat for understand JSON Results
                Log.i("Resultado: ", results)
            }

            try {
                val listaAcercade = ArrayList<AcercadeModel>()
                // create JSONObject from string response
                val rootJsonObject = JSONObject(results)
//                val isSucess = rootJsonObject.getString("noticias")
                val acercaObject = rootJsonObject.getString("nosotros")
                val acercaArray = JSONArray(acercaObject)
                for (i in 0 until acercaArray.length()) {
                    // Get single JSON object node
                    val sObject = acercaArray.get(i).toString()
                    val mItemObject = JSONObject(sObject)
                    // Get String value from json object
                    val nosotrosId = mItemObject.getString("nosotros_id")
                    val nosotrosImg =  mItemObject.getString("nosotros_img")
                    val nosotrosStatus = mItemObject.getString("nosotros_status")
                    val nosotrosDesc = mItemObject.getString("nosotros_desc")
                    val objeto = AcercadeModel(nosotrosId, nosotrosImg, nosotrosStatus, nosotrosDesc)
                    listaAcercade.add(objeto)
                }

                linearLayoutManager = LinearLayoutManager(applicationContext)
                val rvNoticias = findViewById<RecyclerView>(R.id.rvNoticias)
                rvNoticias.layoutManager = linearLayoutManager
                rvNoticias.adapter = AcercadeAdapter(this@AcercaDeActivity, listaAcercade)
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
