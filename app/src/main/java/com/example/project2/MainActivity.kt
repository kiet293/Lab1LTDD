package com.example.project2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.project2.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val myViewModel : MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            val a = binding.etNumber1.text.toString().toIntOrNull()?: 0
            val b = binding.etNumber2.text.toString().toIntOrNull()?: 0
            myViewModel.add(a,b)
        }

        binding.btnSubtract.setOnClickListener {
            val a = binding.etNumber1.text.toString().toIntOrNull()?: 0
            val b = binding.etNumber2.text.toString().toIntOrNull()?: 0
            myViewModel.subtract(a,b)
        }

        myViewModel.message.observe(this@MainActivity, Observer{message ->
            binding.tvResult.text =  "Result: " + message.toString()
        })
    }




}