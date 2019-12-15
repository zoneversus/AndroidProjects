package com.example.application2.Activity.activities

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
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.application2.R
import com.example.application2.actividades.Menu
import com.example.application2.task.RegisterTask
import com.example.application2.utils.SharedPreference
import com.example.application2.utils.Validation
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.edit_profile_img
import kotlinx.android.synthetic.main.activity_register.etLastName
import kotlinx.android.synthetic.main.activity_register.etLastName2
import kotlinx.android.synthetic.main.activity_register.etMail
import kotlinx.android.synthetic.main.activity_register.etName
import kotlinx.android.synthetic.main.activity_register.etPass
import kotlinx.android.synthetic.main.activity_register.profile_image
import kotlinx.android.synthetic.main.activity_update_profile.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.ByteArrayOutputStream
import java.io.File

class Register : AppCompatActivity() {
    private val TAKE_PHOTO_REQUEST = 101
    private var mCurrentPhotoPath: String = ""
    private lateinit var fileToSent: File
    private var imageSelect=false
    val TAG = "CameraDemo"
    companion object {
        //image pick codigo
        private val IMAGE_PICK_CODE = 1000
        //Permission codigo
        private val PERMISSION_CODE = 1001

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        btnRegistrar?.setOnClickListener {
            if (Validation.verifyAvailableNetwork(this)){
                if (Validation.isEmailValid(etMail.text.toString())){
                    if (validateFields()){
                        if (!imageSelect){
                            doRegisterNoImage()
                        }else{
                            doRegister()
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
        tvIniciar?.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
        profile_image?.setOnClickListener {
            showDialog()
        }
        edit_profile_img?.setOnClickListener {
            showDialog()
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
                    requestPermissions(permissions, Register.PERMISSION_CODE)
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
    private fun doRegister() {

        doAsync {
            val user = RegisterTask.registerUser(getString(R.string.ws_url_login),etMail.text.toString(),fileToSent
                ,etName.text.toString(),etLastName.text.toString(),
                etLastName2.text.toString(),etPass.text.toString())
            uiThread {
                if(user.valido == "1") {
                    Log.i("respuesta",user.toString())
                    SharedPreference(applicationContext).save("usuario",user.user)
                    SharedPreference(applicationContext).save("userLogged",user.user.nombre)
                    val intent = Intent(applicationContext, Menu::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()

                }else{
                    showErrorDialog()
                }
            }
        }

    }
    private fun doRegisterNoImage() {

        doAsync {
            val user = RegisterTask.registerUserNoImage(getString(R.string.ws_url_login),etMail.text.toString()
                ,etName.text.toString(),etLastName.text.toString(),
                etLastName2.text.toString(),etPass.text.toString())
            uiThread {
                if(user.valido == "1") {
                    Log.i("respuesta",user.toString())
                    SharedPreference(applicationContext).save("usuario",user.user)
                    SharedPreference(applicationContext).save("userLogged",user.user.nombre)
                    val intent = Intent(applicationContext, Menu::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()

                }else{
                    showErrorDialog()
                }
            }
        }

    }

    private fun validateFields(): Boolean{
        if (Validation.verifyAvailableNetwork(this)){
            if (Validation.isEmailValid(etMail.text.toString())){
                if (!etMail.text.isEmpty()&&etName.text.length>2&&etLastName.text.length>2&&etLastName2.text.length>2&&etPass.text.length>2&&etPassConfirm.length()>2){
                    if(etPass.text.toString()==etPassConfirm.text.toString()){
                    return true
                     }else{
                        Toast.makeText(this,"Checa que las contraseñas coincidan",Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(this,"Verifica que los campos tengan mas de 3 caracteres",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this,"Verifica que el correo sea valido",Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this,"Checa tu conexion a internet",Toast.LENGTH_LONG).show()
        return false
        }
        return false
    }
    private fun showErrorDialog() {
        alert("Ha ocurrido un error, intantelo de nuevo.") {
            yesButton {dismiss() }
        }.show()
    }
    //handle result para la imagen q se selecciono
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == Register.IMAGE_PICK_CODE){
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
    private fun pickImageFromGallery() {
        //Intent para abrir la galeria image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
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
            .insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
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
        profile_image.setImageBitmap(bitmap)
        imageSelect=true

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun showSuccesfulDialog() {
        alert("Los datos han sido actualizados con exito") {
            yesButton {dismiss() }
        }.show()
    }
}
