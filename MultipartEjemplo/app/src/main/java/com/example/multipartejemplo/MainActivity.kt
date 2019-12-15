package com.example.multipartejemplo

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.doAsync
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class MainActivity : AppCompatActivity() {
    private var mediaPath: String? = null
    private var postPath: String? = null
    private val REQUEST_PICK_PHOTO = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pickImage.setOnClickListener {
            choosePhotoFromGallary()
        }
        upload.setOnClickListener {
            uploadFile()
        }

    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if ( requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    // Get the Image from data
                    val selectedImage = data.data
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

                    val cursor =
                        contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                    assert(cursor != null)
                    cursor!!.moveToFirst()

                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    mediaPath = cursor.getString(columnIndex)
                    // Set the Image in ImageView for Previewing the Media
                    previewImg.setImageBitmap(BitmapFactory.decodeFile(mediaPath))
                    cursor.close()
                    postPath = mediaPath
                }
            } else if (resultCode != Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Error lo sentimos!", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getString(R.string.base_ws))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Uploading Image
    private fun uploadFile() {
        if (postPath == null || postPath == "") {
            Toast.makeText(this, "please select an image ", Toast.LENGTH_LONG).show()
            return
        } else {
            doAsync {

                var photoFile: File? = null
                photoFile = File(postPath)
                val partes = ArrayList<MultipartBody.Part>()
                partes.add(
                    MultipartBody.Part.createFormData("ApiCall", "setRegisterUsr")
                )
                partes.add(
                    MultipartBody.Part.createFormData("email", "1")
                )
                partes.add(
                    MultipartBody.Part.createFormData("nombre", "1")
                )
                partes.add(
                    MultipartBody.Part.createFormData("app", "1")
                )
                partes.add(
                    MultipartBody.Part.createFormData("apm", "1")
                )
                partes.add(
                    MultipartBody.Part.createFormData("nip", "123")
                )
                partes.add(
                    MultipartBody.Part.createFormData(
                        "archivo",
                        photoFile?.name,
                        photoFile?.crearMultiparte()
                    )
                )

                val getResponse =
                    getRetrofit().create(ApiConfig::class.java).updateProfile(partes)?.execute()
                if (getResponse != null) {

                    val call = getResponse?.body() as ServerResponse
                    Log.i("Resp", "${call.valido} ")
                    Log.i("Resp", "${call.user.ruta} ")

                }
            }
        }
    }

    fun File.crearMultiparte(): RequestBody {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(this).toString())
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        Log.d("ImagenUtil", "uri: " + Uri.fromFile(this))
        Log.d("ImagenUtil", "type: " + type!!)
        return RequestBody.create(
            MediaType.parse("image/*"), this
        )
    }
}
