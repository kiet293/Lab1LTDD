package com.example.project2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyViewModel:ViewModel() {
    val _message = MutableLiveData<Int>()
    val message:LiveData<Int>
        get() = _message

    init {
        _message.value = 0
    }

//    fun updateValue(v:String) {
//        _message.value = v
//    }

    fun add(a:Int, b:Int) {
        viewModelScope.launch {
            kotlinx.coroutines.delay(3000L)
            _message.value = (a + b)
        }
    }

    fun subtract(a: Int, b: Int) {
        viewModelScope.launch {
            kotlinx.coroutines.delay(3000L)
            _message.value = (a - b)
        }
    }
}