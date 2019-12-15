package com.example.noticiascognitus.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noticiascognitus.R
import com.example.noticiascognitus.adapter.CasaAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_menu.*

class CasaActivity : AppCompatActivity() {

    val casaList: ArrayList<String> = ArrayList()

    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_casa)
        setTitle("Casas disponibles")

        casaList.add("Casa numero 1")
        casaList.add("Casa numero 2")
        casaList.add("Casa numero 3")

        linearLayoutManager = LinearLayoutManager(this)

        val rvCasas = findViewById<RecyclerView>(R.id.rvCasas)
        rvCasas!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        rvCasas.layoutManager = linearLayoutManager
        rvCasas.adapter = CasaAdapter(this, casaList)

        }
    }