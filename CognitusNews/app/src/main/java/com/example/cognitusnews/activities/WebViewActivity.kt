package com.example.cognitusnews.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.cognitusnews.R

class WebViewActivity : AppCompatActivity() {

        var mywebview: WebView? = null
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_web_view)
            mywebview = findViewById<WebView>(R.id.webview)
            // show backbutton and set custom title on actionbar
            val actionBar = supportActionBar
            if (actionBar != null) {
                actionBar.title = "Detalle de la noticia"
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setDisplayShowHomeEnabled(true)
            }
            val nUrl: String = intent.getStringExtra("news_url")
            mywebview!!.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
            mywebview!!.loadUrl(nUrl)
        }

        override fun onSupportNavigateUp(): Boolean {
            onBackPressed()
            return true
        }
    }

