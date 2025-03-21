package com.example.project2

import android.app.Activity
import android.app.AlertDialog
import android.app.ComponentCaller
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project2.databinding.ActivityAdvanceBinding
import com.example.project2.databinding.ActivityListViewBinding
import com.example.project2.databinding.ActivityMain2Binding

open class ListView : AppCompatActivity() {
    lateinit var binding: ActivityListViewBinding
    var data = ArrayList<Student>()
    lateinit var adapter:CustomAdapter
    var selectedIndex: Int = -1

    companion object{
        const val ADVANCE_REQUEST_CODE = 1
        const val EXTRA_STUDENT_LIST = "student_list"
        const val EXTRA_UPDATED_STUDENT_LIST = "updated_student_list"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data)
        adapter = CustomAdapter(this, R.layout.layout_item, data)
        binding.listStudent.adapter = adapter

        events()

    }

    private fun events() {
        binding.listStudent.setOnItemClickListener { _, _, position, _ ->
            selectedIndex = position
            // Hiển thị dữ liệu đã chọn trong input fields để dễ chỉnh sửa
            binding.inputID.setText(data[position].id)
            binding.inputName.setText(data[position].name.removePrefix(". "))
            showToast("Đã chọn: ${data[position]}")
        }

        binding.btnAdd.setOnClickListener {
            addStudent()
        }

        binding.btnRemove.setOnClickListener {
            removeSelectedStudent()
        }


        binding.btnUpdate.setOnClickListener {
            updateSelectedStudent()
        }

        binding.btnAdvance.setOnClickListener {
            val intent = Intent(this, Advance::class.java)
//            intent.putStringArrayListExtra("current_data", ArrayList(data.map { it.id + " - " + it.name }))
            val bundle = Bundle()
            bundle.putSerializable(EXTRA_STUDENT_LIST, ArrayList(data))
            intent.putExtras(bundle)
            startActivityForResult(intent,1)
        }

    }

    private fun addStudent() {
        val inputId = binding.inputID.text.toString().trim()
        val inputName = binding.inputName.text.toString().trim()
        if (inputId.isNotEmpty() && inputName.isNotEmpty()) {
            val std = Student(inputId, ". $inputName")
            data.add(std)
            adapter.notifyDataSetChanged()
            clearTextFields()
            showToast("Thêm thành công!")
            hideKeyboarch()
        } else {
            showToast("Nhập đầy đủ thông tin!")
        }
    }

    private fun removeSelectedStudent() {
        if(selectedIndex != -1) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Xác nhận xóa")
            builder.setMessage("Bạn có muốn xóa mục này không?")

            builder.setPositiveButton("Đồng ý") {dialog, _ ->
                data.removeAt(selectedIndex)
                adapter.notifyDataSetChanged()
                showToast("Xóa thành công")
                selectedIndex = -1
                clearTextFields()
            }

            builder.setNegativeButton("Không") {dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        } else {
            showToast("Hãy chọn mục để xóa")
            clearTextFields()
        }
    }

    private fun updateSelectedStudent() {
        val newValueId = binding.inputID.text.toString().trim()
        val newValueName = binding.inputName.text.toString().trim()
        if (selectedIndex != -1 && newValueId.isNotEmpty() && newValueName.isNotEmpty()) {
            data[selectedIndex].id = newValueId
            data[selectedIndex].name = newValueName
            adapter.notifyDataSetChanged()
            clearTextFields()
            showToast("Cập nhật thành công!")
        } else {
            showToast("Thao tác không hợp lệ")
        }
    }

    private fun clearTextFields() {
        binding.inputID.text?.clear()
        binding.inputName.text?.clear()
    }


    private fun showToast(msg:String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    private fun hideKeyboarch() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADVANCE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            try {
                @Suppress("UNCHECKED_CAST")
                val updateList = data.getSerializableExtra(EXTRA_UPDATED_STUDENT_LIST) as? ArrayList<Student>

                if(updateList != null) {
                    this.data.clear()
                    this.data.addAll(updateList)
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                showToast("Đã xảy ra lỗi khi nhận dữ liệu: ${e.message}")
            }

        }
    }

}