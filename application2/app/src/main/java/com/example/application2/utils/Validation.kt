package com.example.application2.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

class Validation {
    companion object {
        val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

        fun isEmailValid(email: String): Boolean {
            return EMAIL_REGEX.toRegex().matches(email)
        }

        fun verifyAvailableNetwork(activity: AppCompatActivity): Boolean {
            val connectivityManager =
                activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
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

    }
}