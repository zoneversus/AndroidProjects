package com.example.application2.actividades

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import com.bumptech.glide.Glide
import com.example.application2.R
import com.example.application2.model.UserModel
import com.example.application2.task.UpdateProfileTask
import com.example.application2.utils.SharedPreference
import com.example.application2.utils.Validation
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_update_profile.*
import kotlinx.android.synthetic.main.activity_update_profile.etPass
import kotlinx.android.synthetic.main.custom_dialog.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.File

class UpdateProfile : AppCompatActivity() {
    private val TAKE_PHOTO_REQUEST = 101
    private var mCurrentPhotoPath: String = ""
    private lateinit var fileToSent: File
    private var imageSelect=false
    val TAG = "CameraDemo"
    lateinit var usuario:UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)
        supportActionBar?.hide()
        profile_image?.setOnClickListener {
        showDialog()
        }
        edit_profile_img?.setOnClickListener {
        showDialog()
        }
        btn_back_menu?.setOnClickListener {
            val intent = Intent(this,Menu::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
        btnUpdate?.setOnClickListener {
            if (Validation.verifyAvailableNetwork(this)){
                if (Validation.isEmailValid(etMail.text.toString())){
                    if (doValidate()){
                        if (!imageSelect){
                        doUpdateNoImage()

                        }else{
                        doUpdate()
                        }
                    }else{
                        Toast.makeText(this,"Asegurate de que los campos sean mayores a 3 caracteres",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this,"Asegurate de que el email sea valido",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Asegurate de tener conexion a internet",Toast.LENGTH_SHORT).show()
            }
        }
        usuario= SharedPreference(applicationContext).getValueUserModel("usuario") as UserModel
        etName?.text= Editable.Factory.getInstance().newEditable(usuario.nombre)
        etLastName2?.text= Editable.Factory.getInstance().newEditable(usuario.app)
        etLastName?.text= Editable.Factory.getInstance().newEditable(usuario.apm)
        etMail?.text= Editable.Factory.getInstance().newEditable(usuario.email)
        val requestManager = Glide.with(this)
        val imageUri ="http://35.155.161.8:8080/WSExample/"+ usuario.ruta
        val requestBuilder = requestManager.load(imageUri)
        requestBuilder.into(profile_image)

    }
    private fun doUpdate() {
        doAsync {
            val user = UpdateProfileTask.updateUser(getString(R.string.ws_url_login),fileToSent,usuario.id,etMail.text.toString()
                ,etName.text.toString(),etLastName?.text.toString(),etLastName2.text.toString(),etPass.text.toString())
            uiThread {
                if(user.valido == "1") {
                    Log.i("respuesta",user.toString())
                    Log.i("nombre",fileToSent.name)
                    val newUsuario=UserModel(usuario.id,user.user.ruta,etMail.text.toString(),etPass.text.toString()
                        ,etName.text.toString(),etLastName.text.toString(),etLastName2.text.toString())
                    SharedPreference(applicationContext).save("usuario",newUsuario)
                    SharedPreference(applicationContext).save("userLogged",newUsuario.nombre)
                   showSuccesfulDialog()
                }else{
                    showErrorDialog()
                }
            }
        }
    }
    private fun doUpdateNoImage(){
        doAsync {
            val user = UpdateProfileTask.updateUserNoImage(getString(R.string.ws_url_login),usuario.id,etMail.text.toString()
                ,etName.text.toString(),etLastName?.text.toString(),etLastName2.text.toString(),etPass.text.toString())
            uiThread {
                if(user.valido == "1") {
                    Log.i("respuesta",user.toString())
                    val newUsuario=UserModel(usuario.id,usuario.ruta,etMail.text.toString(),etPass.text.toString()
                        ,etName.text.toString(),etLastName?.text.toString(),etLastName2.text.toString())
                    SharedPreference(applicationContext).save("usuario",newUsuario)
                    SharedPreference(applicationContext).save("userLogged",newUsuario.nombre)
                    showSuccesfulDialog()
                }else{
                    showErrorDialog()
                }
            }
        }
    }
    private fun doValidate():Boolean{
        if (etPass.text.length>3&&etLastName.text.length>3&&etLastName2.text.length>3&&
                etName.text.length>3&&etMail.text.length>3){
            return true
        }
        return false
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
            profile_image.setImageURI(data?.data)
            var rutaC=data?.data?.path
            rutaC=rutaC?.replace("/raw","")
            fileToSent=File(rutaC)
            imageSelect=true
            Toast.makeText(this, "Dir->"+data?.dataString, Toast.LENGTH_SHORT).show()
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
        profile_image.setImageBitmap(bitmap)
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
