package com.example.multiplevalidations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.multiplevalidations.databinding.ActivityMainBinding
import com.example.multiplevalidations.extension.hasOnlyNumbers
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.setContentView(binding.root)

        val watcher = ValidationWatcher(binding.edtField) {
            Log.d("LOG", "key: ${it.second} | value: ${it.first}")
        }
        binding.edtField.addTextChangedListener(watcher)
    }
}