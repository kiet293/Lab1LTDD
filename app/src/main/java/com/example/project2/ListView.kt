package com.example.project2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project2.databinding.ActivityListViewBinding
import com.example.project2.databinding.ActivityMain2Binding

open class ListView : AppCompatActivity() {
    lateinit var binding: ActivityListViewBinding
    var data = ArrayList<String>()
    lateinit var adapter:ArrayAdapter<String>
    var selectedIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data)
        binding.listStudent.adapter = adapter
        events()

    }

    private fun events() {
        binding.btnAdd.setOnClickListener {
            val inputData = binding.txtInput.text.toString().trim()

            if (inputData.isNotEmpty()) {
                data.add(inputData)
                adapter.notifyDataSetChanged()
                binding.txtInput.text?.clear()
                showToast("Thêm thành công !")
                hideKeyboarch()
            }

        }

        binding.listStudent.setOnItemClickListener { _, _, position, _ ->
            selectedIndex = position
            showToast("Đã chọn: ${data[position]}")
        }

        binding.btnRemove.setOnClickListener {
            if (selectedIndex != -1) {
                data.removeAt(selectedIndex)
                adapter.notifyDataSetChanged()
                selectedIndex = -1
                hideKeyboarch()
            } else {
                showToast("Hãy chọn một mục để xóa!")
            }
        }

        binding.btnUpdate.setOnClickListener {
            val newValue = binding.txtInput.text.toString().trim()
            if(selectedIndex != -1 && newValue.isNotEmpty()) {
                data[selectedIndex] = newValue
                adapter.notifyDataSetChanged()
                binding.txtInput.text?.clear()
                selectedIndex = -1
                showToast("Cập nhật thành công!")
                hideKeyboarch()
            } else {
                showToast("Hãy chọn 1 item để cập nhật")

            }
        }

    }

    private fun showToast(msg:String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    private fun hideKeyboarch() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}