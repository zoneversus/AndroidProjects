package com.example.application2.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.application2.R
import com.example.application2.model.ProductoModel
import com.example.application2.task.LoginTask
import com.example.application2.task.PedidoTask
import com.example.application2.utils.SharedPreference
import com.example.application2.utils.Validation
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_products_detail.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import kotlinx.android.synthetic.main.custom_dialog.view.mensaje
import kotlinx.android.synthetic.main.default_dialog.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class ProductsDetail : AppCompatActivity() {
    lateinit var producto: ProductoModel
    lateinit var pBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_detail)
        val actionbar = supportActionBar
        pBar = findViewById(R.id.pBar)
        //set actionbar title
        actionbar!!.title = "Producto"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        producto=intent.getParcelableExtra<ProductoModel>("producto")
        Log.i("valor noticia","---"+producto.toString())
        tvDesc.text=producto.description
        tvTitle.text=producto.title
        tvPrice.text="$ "+producto.price+" MXN"
        val requestManager = Glide.with(this)
        val imageUri = getString(R.string.ws_url_login)+producto.image
        val requestBuilder = requestManager.load(imageUri)
        requestBuilder.into(ivFoto)
        pedido?.setOnClickListener {
            if (Validation.verifyAvailableNetwork(this)) {
                doPurchase()
            }else{
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
    private fun doPurchase(){
        doAsync {
            val user = PedidoTask.doPedido(getString(R.string.ws_url_login),producto.id.toString())
            uiThread {
                if(user.valido == "1") {
                    Log.i("respuesta",user.toString())
                    val mDialogView = LayoutInflater.from(this@ProductsDetail).inflate(R.layout.default_dialog, null)
                    val title = TextView(this@ProductsDetail)
                    title.setText("Gracias por su pedido")
                    title.setPadding(10, 10, 10, 10)
                    title.gravity = Gravity.CENTER
                    title.textSize = 20F
                    mDialogView.mensaje.setText("Sera contactado en breve su numero de orden es #"+user.respuesta.numeroOrden)
                    //AlertDialogBuilder
                    val mBuilder = AlertDialog.Builder(this@ProductsDetail)
                        .setView(mDialogView)
                        .setCustomTitle(title)

                    //show dialog
                    val  mAlertDialog = mBuilder.show()
                    mDialogView.dialogSend.setOnClickListener {
                        //dismiss dialog
                        mAlertDialog.dismiss()
                    }

                }else{
                    showErrorDialog()
                }
            }
        }
    }
    private fun showErrorDialog() {
        alert("Ha ocurrido un error, intentelo de nuevo.") {
            yesButton {dismiss() }
        }.show()
    }
}
