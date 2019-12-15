package com.example.tareas.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.example.tareas.R
import com.example.tareas.databinding.ActivityCheckInBinding
import com.example.tareas.databinding.ActivityRegistroBinding
import com.example.tareas.model.UsuarioModel
import com.example.tareas.task.CheckInTask
import com.example.tareas.util.CodesServices
import com.example.tareas.util.Helper
import com.example.tareas.util.SharedPreference
import com.example.tareas.util.Toolbar
import com.github.gcacace.signaturepad.views.SignaturePad
import kotlinx.android.synthetic.main.activity_check_in.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CheckIn: Toolbar(){
    private lateinit var fileToSent: File
    private lateinit var timeInString:String
    private var isSinged=false
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityCheckInBinding>(this, R.layout.activity_check_in)
    }
    companion object {
        val TAG = "PermissionDemo"
        private const val REQUEST_INTERNET = 200
    }

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inicializarToolbar(binding.tool, 2,"Registro")
        revisaPermiso()
        val time =  Calendar.getInstance().time
        timeInString = time.toString("HH:mm")
        binding.etNombre.text= Editable.Factory.getInstance().newEditable(timeInString)
        signaturePad.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {

            }

            override fun onSigned() {
                binding.btnIniciar.isEnabled = true
                isSinged=true
            }

            override fun onClear() {
            }
        })

        binding.btnIniciar.setOnClickListener {
            if (isSinged){
            val signatureBitmap: Bitmap = signaturePad.transparentSignatureBitmap
            if (addJpgSignatureToGallery(signatureBitmap)) { //Toast.makeText(SignActivity.this, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
            doCheckIn()
            } else {
                Toast.makeText(
                    this,
                    "Unable to store the signature",
                    Toast.LENGTH_SHORT
                ).show()
            }
            }else{
                Toast.makeText(
                    this,
                    "Firma antes de enviar el registro",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }
    @ExperimentalStdlibApi
    private fun doCheckIn(){
        doAsync {
            val usuarioId =  SharedPreference(applicationContext).getValueString("userLogged")
            val word= Helper.stringTo64(CodesServices.CHECK_IN+"|"+usuarioId+"|"+timeInString+"|checkInApp")
            val user = CheckInTask.checkInUser(getString(R.string.url_service),word,fileToSent)
            uiThread {
                if(user.valido == "1") {
                    Log.i("respuesta",user.toString())
                    isSinged=false
                    showSuccesfulDialog()
                    val intent = Intent(applicationContext,Menu::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    showErrorDialog()
                }
            }
        }
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
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_INTERNET -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Si dio permiso")
            }else{
                Log.i(TAG, "No dio permiso")
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
            fileToSent = File(fileFirm, "Firma.png")
            Log.d("Files", "Path: $fileToSent")
            saveBitmapToPNG(signature, fileToSent)

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


    private fun showErrorDialog() {
        alert("Ha ocurrido un error, intÃ©ntelo de nuevo.") {
            yesButton {dismiss() }
        }.show()
    }
    private fun showSuccesfulDialog() {
        alert("Los datos han sido actualizados con exito") {
            yesButton {dismiss() }
        }.show()
    }
}
