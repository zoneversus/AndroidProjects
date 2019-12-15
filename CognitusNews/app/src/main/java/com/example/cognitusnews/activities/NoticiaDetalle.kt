package com.example.cognitusnews.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.cognitusnews.R
import com.example.cognitusnews.model.NoticiaModel
import kotlinx.android.synthetic.main.activity_noticia_detalle.*

class NoticiaDetalle : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticia_detalle)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Noticia"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        val noticia=intent.getParcelableExtra<NoticiaModel>("noticia")
        Log.i("valor noticia","---"+noticia.toString())
        tvDesc.text=noticia.notDesc
        tvTitle.text=noticia.notTitulo
        bCompartir?.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.mensaje_compartir)+noticia.notUrl);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.titulo_compartir)))
        }
        bVerMas?.setOnClickListener {
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("news_url", ""+noticia.notUrl)
                startActivity(intent)
        }
        val requestManager = Glide.with(this)
        val imageUri = noticia.notImg
        val requestBuilder = requestManager.load(imageUri)
        requestBuilder.into(ivFoto)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
