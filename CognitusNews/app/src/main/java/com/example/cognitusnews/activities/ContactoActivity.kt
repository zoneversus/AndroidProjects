package com.example.cognitusnews.activities

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cognitusnews.R
import com.example.cognitusnews.adapter.NoticiasAdapter
import com.example.cognitusnews.model.NoticiaModel
import com.example.cognitusnews.tasks.ApiGetPostHelper
import com.example.cognitusnews.utilities.Utils
import kotlinx.android.synthetic.main.activity_contacto.*
import kotlinx.android.synthetic.main.dialog_pedido.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ContactoActivity : AppCompatActivity() {
    lateinit var pBar: ProgressBar
    lateinit var user: String
    lateinit var mail: String
    lateinit var message: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacto)
        pBar = findViewById(R.id.pBar)
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Contacto"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        btnSend?.setOnClickListener {

        user=etUser.text.toString()
        mail=etMail.text.toString()
        message=etMessage.text.toString()
            if (user.length>0&&mail.length>0&&message.length>0){
                if (Utils.isEmailValid(mail)){
                    if (Utils.verifyAvailableNetwork(this)) {
                    sentContact().execute() }else{
                        Toast.makeText(
                            applicationContext,
                            "Asegurese de tener conexion a internet",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }else{
                    Toast.makeText(
                        applicationContext,
                        "Ingrese un e-mail valido",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else{
                Toast.makeText(
                    applicationContext,
                    "Asegurese de llenar todos los datos",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // Inner class Async to consume WS
    @SuppressLint("StaticFieldLeak")
    inner class sentContact : AsyncTask<Void, Void, String>() {

        override fun onPreExecute() {
            // show progressbar for UI experience
            pBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg voids: Void): String {
            // Here is post Json api example
            val sendParams = HashMap<String, String>()
            // Send parameters and value on JSON api
            sendParams["nombre"] = user
            sendParams["correo"] = mail
            sendParams["mensaje"] = message
            // send the HttpPostRequest by HttpURLConnection and receive a Results in return string
            return ApiGetPostHelper.SendParams(getString(R.string.ws_url_contacto), sendParams)
        }

        override fun onPostExecute(results: String?) {
            // Hide Progressbar
            pBar.visibility = View.GONE

            if (results != null) {
                // See Response in Logcat for understand JSON Results
                Log.i("Resultado: ", results)
            }

            try {

                // create JSONObject from string response
                val rootJsonObject = JSONObject(results)
//                val isSucess = rootJsonObject.getString("noticias")
                val validMesageObject = rootJsonObject.getInt("valido")
                Log.i("hijo",validMesageObject.toString())
                    if (validMesageObject==1){
                val mDialogView = LayoutInflater.from(this@ContactoActivity).inflate(R.layout.dialog_pedido, null)
                val title = TextView(this@ContactoActivity)
                title.setText("Gracias")
                title.setPadding(10, 10, 10, 10)
                title.gravity = Gravity.CENTER
                title.textSize = 20F
                //AlertDialogBuilder
                val mBuilder = AlertDialog.Builder(this@ContactoActivity)
                    .setView(mDialogView)
                    .setCustomTitle(title)
                //show dialog
                val  mAlertDialog = mBuilder.show()
                etUser.text.clear()
                etMail.text.clear()
                etMessage.text.clear()
                mDialogView.mensaje.text=getString(R.string.mensaje_contacto)
                mDialogView.dialogSend.setOnClickListener {
                    //dismiss dialog
                    mAlertDialog.dismiss()
                }
                    }else{
                        Toast.makeText(
                            applicationContext,
                            "Lo sentimos, algo salio mal. ¡Intenta de nuevo!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

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

