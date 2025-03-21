package com.example.project2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.project2.databinding.ActivityAdvanceBinding

class Advance : AppCompatActivity() {
    lateinit var binding: ActivityAdvanceBinding
    lateinit var data:ArrayList<Student>
    lateinit var adapter:CustomAdapter

    companion object {
        const val EXTRA_STUDENT_LIST = "student_list"
        const val EXTRA_UPDATED_STUDENT_LIST = "updated_student_list"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAdvanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()

        adapter = CustomAdapter(this, R.layout.layout_item, data)
        binding.listStd.adapter = adapter
        events()
    }

    private fun initData() {
        try {
            // Khởi tạo data từ dữ liệu được truyền từ ListView
            data = if (intent.hasExtra("student_list")) {
                @Suppress("UNCHECKED_CAST")
                intent.getSerializableExtra("student_list") as ArrayList<Student>
            } else {
                ArrayList()
            }
        } catch (e:Exception) {
            showToast("Lỗi khi tải dữ liệu: ${e.message}")
            data = ArrayList()
        }
    }


    private fun events() {
        binding.btnAdd.setOnClickListener {
            addStudent()
        }

        binding.btnBack.setOnClickListener {
            returnResult()
        }

        }

    private fun addStudent() {
        val inputID = binding.txtId.text.toString().trim()
        val inputName = binding.txtName.text.toString().trim()
        if (inputID.isNotEmpty() && inputName.isNotEmpty()) {
            data.add(Student(inputID, ". $inputName"))
            adapter.notifyDataSetChanged()
            clearText()
            showToast("Thêm thành công!")
            hideKeyboarch()
        } else {
            showToast("Vui lòng nhập đầy đủ thông tin!")
        }
    }

    private fun returnResult() {
        val replyIntent = Intent()
        val bundle = Bundle()
        bundle.putSerializable(EXTRA_UPDATED_STUDENT_LIST, data)
        replyIntent.putExtras(bundle)
        setResult(RESULT_OK, replyIntent)
        finish()
    }

    private fun clearText() {
        binding.txtId.text?.clear()
        binding.txtName.text?.clear()
    }

    private fun showToast(msg:String) {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }

    private fun hideKeyboarch() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0) }
}