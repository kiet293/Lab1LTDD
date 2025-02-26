package com.example.project2

import android.app.Activity
import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class GiaiPtBac2 : AppCompatActivity() {
    lateinit var btnGiai: Button
    lateinit var btnXoa: Button
    lateinit var numA: EditText
    lateinit var numB: EditText
    lateinit var numC: EditText
    lateinit var ketQua: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ptbac2)
        controls()
        events()
    }

    private fun controls() {
        numA = findViewById(R.id.edtNumA)
        numB = findViewById(R.id.edtNumB)
        numC = findViewById(R.id.edtNumC)
        btnGiai = findViewById(R.id.btnGiai)
        btnXoa = findViewById(R.id.btnXoa)
        ketQua = findViewById(R.id.ketQua)
    }

    private fun events() {
        btnGiai.setOnClickListener {
            val aStr = numA.text.toString()
            val bStr = numB.text.toString()
            val cStr = numC.text.toString()

            // Kiểm tra input có trống không
            if (aStr.isEmpty() || bStr.isEmpty() || cStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ hệ số!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val a = aStr.toDoubleOrNull()
            val b = bStr.toDoubleOrNull()
            val c = cStr.toDoubleOrNull()

            // Kiểm tra giá trị nhập có hợp lệ không
            if (a == null || b == null || c == null) {
                Toast.makeText(this, "Vui lòng nhập số hợp lệ!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = tinhPtBac2(a, b, c)
            var intent = Intent(applicationContext, MainActivity2::class.java)
            intent.putExtra("ptb2", result)
            startActivityForResult(intent, 1)

//            val resultReply = intent.getStringExtra("reply")
//            ketQua.text = "$resultReply"
            hideKeyboarch()
        }


        btnXoa.setOnClickListener {
            numA.text.clear()
            numB.text.clear()
            numC.text.clear()
            ketQua.text = ""
            hideKeyboarch()
            showToast("Đã xóa thành công")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val resultReply = data?.getStringExtra("reply")
            ketQua.text = resultReply ?: "Không có kết quả"
        }
    }

    private fun tinhPtBac2(a: Double, b: Double, c: Double): String {
        return if (a != 0.0) {
            val delta = b * b - 4 * a * c
            if (delta < 0) {
                "Phương trình vô nghiệm"
            } else if (delta == 0.0) {
                val x = -b / (2 * a)
                "Phương trình có 1 nghiệm kép: $x"
            } else {
                val canDelta = sqrt(delta)
                val x1 = (-b + canDelta) / (2 * a)
                val x2 = (-b - canDelta) / (2 * a)
                "Phương trình có 2 nghiệm: $x1 và $x2"
            }
        } else {
            if (b == 0.0) {
                if (c == 0.0) {
                    "Phương trình có vô số nghiệm"
                } else {
                    "Phương trình vô nghiệm"
                }
            } else {
                val x = -c / b
                "Phương trình bậc nhất có 1 nghiệm: $x"
            }
        }
    }

    private fun hideKeyboarch() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


}
