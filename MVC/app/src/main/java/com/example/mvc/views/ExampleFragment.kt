package com.example.mvc.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.mvc.R
import com.example.mvc.controller.ControllerUser
import com.example.mvc.databinding.PerfilFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExampleFragment:Fragment() {
    private lateinit var binding: PerfilFragmentBinding
    lateinit var todoViewModel:UserViewHolder
    lateinit var controllerUser: ControllerUser

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.perfil_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(binding.root, savedInstanceState)
        controllerUser= ControllerUser()

        todoViewModel = ViewModelProviders.of(this).get(UserViewHolder::class.java)

        todoViewModel.user.observe(viewLifecycleOwner , Observer { user ->
            binding.user=user
        })


    }


    fun getUser(){
        lifecycleScope.launch(Dispatchers.IO) {
            val user=controllerUser.getUser()
            todoViewModel.user.postValue(user)
        }
    }

}