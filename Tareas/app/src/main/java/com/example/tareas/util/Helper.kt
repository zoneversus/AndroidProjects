package com.example.tareas.util

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.tareas.R
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

class Helper {
    companion object{
        @ExperimentalStdlibApi
        fun stringTo64(text: String):String{
            val output = Base64.encodeToString(text.encodeToByteArray(), Base64.DEFAULT)
            return output
        }
        fun crearMultiparte(file: File): RequestBody {
            // create RequestBody instance from file
            var type: String? = null
            val extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString())
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
            }
            Log.d("ImagenUtil", "uri: " + Uri.fromFile(file))
            Log.d("ImagenUtil", "type: " + type!!)
            return RequestBody.create(
                MediaType.parse("image/*"), file)
        }
        fun loadImage(contx: Context?, imv: ImageView, url: String, tipoImg: Int) {
            val requestManager = contx?.let { Glide.with(it) }
            var imageUri: String = ""
            if (tipoImg == 1)
                imageUri = contx?.getString(R.string.url_service) + url
            if (tipoImg == 2)
                imageUri = url
            val requestBuilder = requestManager?.load(imageUri)
            requestBuilder?.into(imv)
        }

    }
}