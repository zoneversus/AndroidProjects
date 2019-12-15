package com.example.tareas.activities

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.tareas.R
import kotlinx.android.synthetic.main.activity_web_view.*


class WebViewActivity : AppCompatActivity() {

    var mywebview: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        mywebview = findViewById(R.id.webview)
        val settings = webview.settings
        settings.javaScriptEnabled = true
        // show backbutton and set custom title on actionbar
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Terminos y condiciones"
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
        webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + nUrl)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
