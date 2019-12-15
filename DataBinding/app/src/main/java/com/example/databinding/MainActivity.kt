package com.example.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.example.databinding.databinding.ActivityMainBinding

class MainActivity : ToolbarActivity(), Validator.ValidationListener {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inicializarToolbar(binding.barraP, "Pantalla principal", 1)

        binding.barraP.ivBtn1.setOnClickListener {
            Toast.makeText(this, "Boton de barra ", Toast.LENGTH_LONG).show()
        }

        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)
        binding.setClickListener {
            when (it!!.id) {
                binding.btnEva.id -> {
                    validator!!.toValidate()
                }
            }
        }
    }

    override fun onValidationSuccess() {
        Toast.makeText(this, "Todo ok ", Toast.LENGTH_LONG).show()
    }

    override fun onValidationError() {
        Toast.makeText(this@MainActivity, "Dados invalidos!", Toast.LENGTH_SHORT).show()
    }
}

