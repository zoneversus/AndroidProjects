package com.example.mvc.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvc.adapter.PublicacionAdapter
import com.example.mvc.R
import com.example.mvc.controller.ControllerPublicaciones
import com.example.mvc.databinding.PublicacionesFragmentBinding
import com.example.mvc.models.PublicacionViewModel

class PublicacionesFragment : Fragment() {
    private lateinit var binding: PublicacionesFragmentBinding
    private lateinit var controller: ControllerPublicaciones
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var animation : LayoutAnimationController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.publicaciones_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(binding.root, savedInstanceState)
        init()
        //behavior when publications list its updated
        controller.getViewHolderPublicaciones().publicaciones.observe(viewLifecycleOwner, Observer { pubList ->
            updateUI(pubList) })
        controller.getPublicaciones()
    }

    private fun init(){
        //Controller is the mediator, where you can get you publications with the backend
        controller = ControllerPublicaciones()
        //Here you declare the animation that will be assigned to the recyclerview
        animation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_anim)
        binding.rvPublicaciones.layoutAnimation=animation
        //This 2 lines of code assign the kind of display that the rv will be following could be linear, grid, etc.
        linearLayoutManager = LinearLayoutManager(context)
        binding.rvPublicaciones.layoutManager = linearLayoutManager
    }

    fun updateUI(lista: List<PublicacionViewModel>) {
        binding.rvPublicaciones.adapter = PublicacionAdapter(lista)
        binding.rvPublicaciones.scheduleLayoutAnimation()
    }


}

