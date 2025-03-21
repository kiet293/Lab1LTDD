package com.example.project2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.project2.databinding.ActivityUpdateStudentBinding

class UpdateStudent : AppCompatActivity() {
    lateinit var binding: ActivityUpdateStudentBinding
    private var studentId = ""
    private var studentName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the student information from the intent
        val receivedStudentInfo = intent.getStringExtra("infoStd")
        studentId = intent.getStringExtra("studentId") ?: ""

        // If we have received student info, set it in the EditText
        if (!receivedStudentInfo.isNullOrEmpty()) {
            binding.txtNameStd.setText(receivedStudentInfo)
        }

        if (studentId.isNotEmpty()) {
            binding.txtID.setText(studentId)
        }

        events()
    }

    private fun events() {
        // Add your update functionality here
        binding.btnSave.setOnClickListener {
            // Get updated values
            val updatedName = binding.txtNameStd.text.toString()
            val updatedId = binding.txtID.text.toString()

            // Create intent to pass back the updated values
            val resultIntent = intent
            resultIntent.putExtra("updatedName", updatedName)
            resultIntent.putExtra("updatedId", updatedId)
            resultIntent.putExtra("position", intent.getIntExtra("position", -1))

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}