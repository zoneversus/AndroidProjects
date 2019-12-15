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
import com.bumptech.glide.Glide
import com.example.cognitusnews.R
import com.example.cognitusnews.model.NoticiaModel
import com.example.cognitusnews.model.ProductoModel
import com.example.cognitusnews.tasks.ApiGetPostHelper
import com.example.cognitusnews.utilities.Utils
import kotlinx.android.synthetic.main.activity_contacto.*
import kotlinx.android.synthetic.main.activity_noticia_detalle.*
import kotlinx.android.synthetic.main.activity_noticia_detalle.ivFoto
import kotlinx.android.synthetic.main.activity_noticia_detalle.tvDesc
import kotlinx.android.synthetic.main.activity_noticia_detalle.tvTitle
import kotlinx.android.synthetic.main.activity_producto_detalle.*
import kotlinx.android.synthetic.main.dialog_pedido.*
import kotlinx.android.synthetic.main.dialog_pedido.view.*
import org.json.JSONException
import org.json.JSONObject

class ProductoDetalle : AppCompatActivity() {
 lateinit var producto:ProductoModel
    lateinit var pBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto_detalle)
        val actionbar = supportActionBar
        pBar = findViewById(R.id.pBar)
        //set actionbar title
        actionbar!!.title = "Producto"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        producto=intent.getParcelableExtra<ProductoModel>("producto")
        Log.i("valor noticia","---"+producto.toString())
        tvDesc.text=producto.prodDesc
        tvTitle.text=producto.prodTitle
        tvPrice.text="$ "+producto.prodPrice+" MXN"
        val requestManager = Glide.with(this)
        val imageUri = producto.prodImg
        val requestBuilder = requestManager.load(imageUri)
        requestBuilder.into(ivFoto)
        pedido?.setOnClickListener {

            if (Utils.verifyAvailableNetwork(this)) {
        sentProducto().execute()}else{
                Toast.makeText(
                    applicationContext,
                    "Asegurese de tener conexion a internet",
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
    inner class sentProducto : AsyncTask<Void, Void, String>() {

        override fun onPreExecute() {
            // show progressbar for UI experience
            pBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg voids: Void): String {
            // Here is post Json api example
            val sendParams = HashMap<String, String>()
            // Send parameters and value on JSON api
            sendParams["id_producto"] = producto.prodID.toString()
            // send the HttpPostRequest by HttpURLConnection and receive a Results in return string
            return ApiGetPostHelper.SendParams(getString(R.string.ws_url_prodOrder), sendParams)
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
                val responseMesageObject = rootJsonObject.getJSONObject("respuesta").getString("orden")
                Log.i("hijo",responseMesageObject.toString())
                if (validMesageObject==1){
                    val mDialogView = LayoutInflater.from(this@ProductoDetalle).inflate(R.layout.dialog_pedido, null)
                    val title = TextView(this@ProductoDetalle)
                    title.setText("Gracias por su pedido")
                    title.setPadding(10, 10, 10, 10)
                    title.gravity = Gravity.CENTER
                    title.textSize = 20F
                    mDialogView.mensaje.setText("Sera contactado en breve su numero de orden es #"+responseMesageObject)
                    //AlertDialogBuilder
                    val mBuilder = AlertDialog.Builder(this@ProductoDetalle)
                        .setView(mDialogView)
                        .setCustomTitle(title)

                    //show dialog
                    val  mAlertDialog = mBuilder.show()
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
