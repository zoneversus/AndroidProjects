package com.example.perritosretrofit.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perritosretrofit.Model.DogModel
import com.example.perritosretrofit.R
import com.example.perritosretrofit.adapter.DogAdapter
import com.example.perritosretrofit.task.APIService
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(){
    lateinit var imagesPuppies:List<String>
    lateinit var dogsAdapter:DogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSearch.setOnClickListener {
            searchByName(searchBreed.text.toString().toLowerCase())
        }
    }
    private fun searchByName(query: String) {
        doAsync {
            val call = getRetrofit().create(APIService::class.java).getCharacterByName("$query/images").execute()
            val puppies = call.body() as DogModel
            uiThread {
                if(puppies.status == "success") {
                    initCharacter(puppies)
                }else{
                    showErrorDialog()
                }
            }
        }
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun initCharacter(puppies: DogModel) {
        if(puppies.status == "success"){
            imagesPuppies = puppies.images
        }
        dogsAdapter = DogAdapter(imagesPuppies)
        rvDogs.setHasFixedSize(true)
        rvDogs.layoutManager = LinearLayoutManager(this)
        rvDogs.adapter = dogsAdapter
    }

    private fun showErrorDialog() {
        alert("Ha ocurrido un error, intÃ©ntelo de nuevo.") {
            yesButton { }
        }.show()
    }
}
