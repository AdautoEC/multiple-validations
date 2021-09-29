package com.example.multiplevalidations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.multiplevalidations.databinding.ActivityMainBinding
import com.example.multiplevalidations.extension.hasOnlyNumbers
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var tipoChave: PIXKey = PIXKey.CHAVE_INVALIDA
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.setContentView(binding.root)

        val watcher = ValidationWatcher(binding.edtField)
        watcher.onKeyChoosed = {
            tipoChave = it.second
            if(it.first.isEmpty()) {
                binding.tilField.error = "Chave inválida"
            } else {
                binding.tilField.error = null
            }
        }
        binding.edtField.addTextChangedListener(watcher)

        binding.button.setOnClickListener {
            Log.v("Button", tipoChave.name)
        }
    }
}