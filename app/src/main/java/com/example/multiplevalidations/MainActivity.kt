package com.example.multiplevalidations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.multiplevalidations.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.setContentView(binding.root)

        val watcher = ValidationWatcher()
        binding.edtField.addTextChangedListener(watcher)
        watcher.setText = {
            binding.edtField.post {
//                binding.edtField.setText(it)
//                binding.edtField.setSelection(it.length)
//                watcher.shouldEdit = false
            }
        }
    }
}