package com.example.tareas.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.bumptech.glide.Glide
import com.example.tareas.R
import com.example.tareas.databinding.ActivityPerfilBinding
import com.example.tareas.databinding.ActivityRegistroBinding
import com.example.tareas.model.UsuarioModel
import com.example.tareas.task.UserTask
import com.example.tareas.util.CodesServices
import com.example.tareas.util.Helper
import com.example.tareas.util.SharedPreference
import com.example.tareas.util.Toolbar
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_perfil.*
import kotlinx.android.synthetic.main.activity_perfil.etCorreo
import kotlinx.android.synthetic.main.activity_perfil.etPassword
import kotlinx.android.synthetic.main.activity_registro.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.File

class Perfil : Toolbar(),  Validator.ValidationListener {
    private val TAKE_PHOTO_REQUEST = 101
    private var mCurrentPhotoPath: String = ""
    private lateinit var fileToSent: File
    private var imageSelect=false
    val TAG = "CameraDemo"
    lateinit var usuario:UsuarioModel

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityPerfilBinding>(this, R.layout.activity_perfil)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inicializarToolbar(binding.tool, 2,"Perfil")
        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)
        binding.setClickListener {
            when (it!!.id) {
                binding.btnIniciar.id -> {
                    validator!!.toValidate()
                }binding.imageProfile.id ->{
                showDialog()
            }
            }
        }
        usuario= SharedPreference(applicationContext).getValueUserModel("usuario") as UsuarioModel
        binding.etCorreo?.text= Editable.Factory.getInstance().newEditable(usuario.usr_email)
        binding.etNombre?.text=Editable.Factory.getInstance().newEditable(usuario.usr_nombre)

        val requestManager = Glide.with(this)
        val imageUri =getString(R.string.url_service)+ usuario.usr_ruta
        val requestBuilder = requestManager.load(imageUri)
        requestBuilder.into(binding.imageProfile)
    }

    override fun onValidationError() {

    }

    @ExperimentalStdlibApi
    override fun onValidationSuccess() {
        if (imageSelect){
            if (binding.etPassword.text.toString() == binding.etPasswordConfirm.text.toString()){
            doUpdate()
            }
        }
    }
    @ExperimentalStdlibApi
    private fun doUpdate() {
        doAsync {
            val rawData = CodesServices.ACTUALIZA+"|"+usuario.usr_id+"|"+binding.etNombre.text.toString()+"|"+
                    binding.etCorreo.text.toString()+"|"+binding.etPassword.text.toString()
            val dataToSend= Helper.stringTo64(rawData)
            val user = UserTask.updateUser(getString(R.string.url_service),dataToSend,fileToSent)
            uiThread {
                if(user.valido == "1") {
                    Log.i("respuesta",user.toString())
                    showSuccesfulDialog()
                    usuario.usr_ruta=user.url
                    usuario.usr_nombre=binding.etNombre.text.toString()
                    SharedPreference(applicationContext).save("usuario",usuario)
                }else{
                    showErrorDialog()
                }
            }
        }
    }
    fun showDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val title = TextView(this)
        title.setText("Elija")
        title.setPadding(10, 10, 10, 10)
        title.gravity = Gravity.CENTER
        title.textSize = 20F
        mDialogView.mensaje.setText("Elija de donde quiere obtener la foto")
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setCustomTitle(title)

        //show dialog
        val  mAlertDialog = mBuilder.show()
        mDialogView.dialogCamera.setOnClickListener {
            revisaPermiso()
            mAlertDialog.dismiss()
        }
        mDialogView.dialogGalery.setOnClickListener {
            //dismiss dialog
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission requerido
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup
                    requestPermissions(permissions, PERMISSION_CODE)
                }
                else{
                    //permission otorgados
                    pickImageFromGallery()
                }
            }
            else{
                //system OS  < Marshmallow
                pickImageFromGallery()
            }
            mAlertDialog.dismiss()
        }

    }

    private fun pickImageFromGallery() {
        //Intent para abrir la galeria image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick codigo
        private val IMAGE_PICK_CODE = 1000
        //Permission codigo
        private val PERMISSION_CODE = 1001
    }

    //handle requested permission resultado
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission otorgado
                    pickImageFromGallery()
                }
                else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result para la imagen q se selecciono
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            binding.imageProfile.setImageURI(data?.data)
            var rutaC=data?.data?.path
            rutaC=rutaC?.replace("/raw","")
            fileToSent=File(rutaC)
            imageSelect=true
        }
        if (resultCode == Activity.RESULT_OK && requestCode == TAKE_PHOTO_REQUEST) {
            processCapturedPhoto()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }
    fun revisaPermiso(){
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                TAKE_PHOTO_REQUEST
            )
            Log.i(TAG, "Pide permiso")
        }else
            launchCamera()

    }

    private fun launchCamera() {
        val values = ContentValues(1)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        val fileUri = contentResolver
            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(packageManager) != null) {
            mCurrentPhotoPath = fileUri.toString()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            startActivityForResult(intent, TAKE_PHOTO_REQUEST)
        }
    }
    private fun processCapturedPhoto() {
        val cursor = contentResolver.query(
            Uri.parse(mCurrentPhotoPath),
            Array(1) {android.provider.MediaStore.Images.ImageColumns.DATA},
            null, null, null)
        cursor?.moveToFirst()
        val photoPath = cursor?.getString(0)
        cursor?.close()
        val file = File(photoPath)
        val uri = Uri.fromFile(file)

        val height = resources.getDimensionPixelSize(R.dimen.height_camera)
        val width = resources.getDimensionPixelSize(R.dimen.width_camera)

        val request = ImageRequestBuilder.newBuilderWithSource(uri)
            .setResizeOptions(ResizeOptions(width, height))
            .build()

        val fileToCompress= File(uri.getPath())

        fileToSent = Compressor(this).compressToFile(fileToCompress)
        val bitmap = Compressor(this).compressToBitmap(fileToCompress)
        imageSelect=true
        binding.imageProfile.setImageBitmap(bitmap)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
