package com.example.project2

open class Student(var id:Int, var name:String) {
    open fun getInfo():String {
        return "$id, name"
    }
}