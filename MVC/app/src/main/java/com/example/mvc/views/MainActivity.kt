package com.example.mvc.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.mvc.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if(null == savedInstanceState) {
            val mainFragment = PublicacionesFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout, mainFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
            supportFragmentManager.executePendingTransactions()
        }

    }

}
