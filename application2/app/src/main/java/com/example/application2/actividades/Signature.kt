package com.example.application2.actividades

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.application2.R
import com.github.gcacace.signaturepad.views.SignaturePad
import kotlinx.android.synthetic.main.activity_signature.*
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@SuppressLint("ByteOrderMark")
class Signature : AppCompatActivity() {
    private var locationManager : LocationManager? = null
    private val LOCATION_REQUEST=1500
    companion object {
        val TAG = "PermissionDemo"
        private const val REQUEST_INTERNET = 200

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signature)
        supportActionBar?.hide()
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        revisaPermiso()
        revisaPermisoLocacion()

        signaturePad.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() { //Toast.makeText(SignActivity.this, "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            override fun onSigned() {
                mSaveButton.isEnabled = true
                mClearButton.isEnabled = true
            }

            override fun onClear() {
                mSaveButton.isEnabled = false
                mClearButton.isEnabled = false
            }
        })

        mSaveButton.setOnClickListener {
            val signatureBitmap: Bitmap = signaturePad.transparentSignatureBitmap
            if (addJpgSignatureToGallery(signatureBitmap)) {
                Toast.makeText(this, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Unable to store the signature",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        mClearButton.setOnClickListener { signaturePad.clear() }
    }

    fun revisaPermiso(){
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_INTERNET
            )
            Log.i(TAG, "Pide permiso")
        }
    }
    fun revisaPermisoLocacion(){
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST
            )
            Log.i(TAG, "Pide permiso")
        }else
            obtenLocacion()

    }
    fun obtenLocacion(){
        try {
            Toast.makeText(this, "Obteniendo locacion...", Toast.LENGTH_SHORT).show()
            // Request location updates
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        } catch(ex: SecurityException) {
            Log.d(TAG, "Security Exception, no location available")
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_INTERNET -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Si dio permiso")
            }else{
                Log.i(TAG, "No dio permiso")
            }
        }
        when (requestCode) {
            LOCATION_REQUEST -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenLocacion()
            }else{
                Toast.makeText(this, "Se requiere el permiso...", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun addJpgSignatureToGallery(signature: Bitmap): Boolean {
        var result = false
        try {
            val path =
                Environment.getExternalStorageDirectory().absolutePath
            Log.d("Files", "Path: $path")
            val fileFirm = File(path)
            fileFirm.mkdirs()
            val photo =
                File(fileFirm, "Firma.png")
            Log.d("Files", "Path: $photo")
            saveBitmapToPNG(signature, photo)
            result = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }

    @Throws(IOException::class)
    fun saveBitmapToPNG(bitmap: Bitmap, photo: File) {
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(photo)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                out?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            txtLocation.text="${location.longitude} : ${location.latitude}"
           }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
}
