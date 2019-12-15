package com.example.noticiascognitus.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.noticiascognitus.R
import kotlinx.android.synthetic.main.activity_newsdetail.*


class DetailNewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newsdetail)
        var iVNew = findViewById<ImageView>(R.id.iVNew)
        var tVTitle = findViewById<TextView>(R.id.tVTitle)
        val urlImg: String = intent.getStringExtra("img_url")
        val title: String = intent.getStringExtra("title")
        val nUrl: String = intent.getStringExtra("news_url")
        val requestManager = Glide.with(this)
        val requestBuilder = requestManager.load(urlImg)
        requestBuilder.into(iVNew)
        tVTitle.setText(title)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Detalle de noticia"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
        tVShare.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.msj_compartir) + nUrl);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.titulo_compartir)))
        }
    }

    // show backbutton on actionbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
