package com.example.project2

class Student(var id: String = "", var name:String = "") : java.io.Serializable {
    override fun toString(): String {
        return "$id$name"
    }

}