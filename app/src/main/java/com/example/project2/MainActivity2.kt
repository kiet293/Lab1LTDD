package com.example.project2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    lateinit var back:Button
    lateinit var result:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        controls()
        val receivedKq = intent.getStringExtra("ptb2")

//        Toast.makeText(this, receivedKq, Toast.LENGTH_SHORT).show()
        result.text = "$receivedKq"
        events()
        hideKeyboarch()
    }

    private fun controls() {
        result = findViewById(R.id.result)
       back = findViewById(R.id.btnBack)
    }

    private fun events() {
        back.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("reply", intent.getStringExtra("ptb2"))
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }

    private fun hideKeyboarch() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }



}