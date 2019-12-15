package com.example.ejemplocoroutines_livedata.domain

import com.example.ejemplocoroutines_livedata.data.CoroutineWebservice
import com.example.ejemplocoroutines_livedata.data.coroutineWS

class TodoCoroutineRepository {
    var client: CoroutineWebservice = coroutineWS

    //se prescinde de la captura de errores para obtener directamente la instacia del objeto
    suspend fun getTodo(id: Int) = client.getTodo(id)
}