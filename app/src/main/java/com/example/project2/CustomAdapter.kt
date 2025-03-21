package com.example.project2

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

open class CustomAdapter(val context:Activity, val layout:Int, val data:ArrayList<Student>) : BaseAdapter() {
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(p0: Int): Any {
        return data.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return data.get(p0).id.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val view = p1 ?: inflater.inflate(layout, p2, false)

        val txtItem = view.findViewById<TextView>(R.id.txtItem)
        val txtId = view.findViewById<TextView>(R.id.txtId)
        val student = data[p0]
        txtId.text = student.id.toString()
        txtItem.text = student.name
        return view
    }

}