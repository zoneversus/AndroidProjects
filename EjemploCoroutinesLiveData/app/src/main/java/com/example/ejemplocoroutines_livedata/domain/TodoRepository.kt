package com.example.ejemplocoroutines_livedata.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ejemplocoroutines_livedata.data.Todo
import com.example.ejemplocoroutines_livedata.data.Webservice
import com.example.ejemplocoroutines_livedata.data.wS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodoRepository {

    var client: Webservice = wS

    fun getTodo(id: Int): LiveData<Todo> {
        val liveData = MutableLiveData<Todo>()

        client.getTodo(id).enqueue(object: Callback<Todo> {
            override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                if (response.isSuccessful) {
                    //cuando la respuesta está lista se actualiza a través de LiveDava
                    liveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Todo>, t: Throwable) {
                t.printStackTrace()
            }
        })

        //se regresa liveData para obtener la referencia de manera síncrona
        return liveData
    }
}