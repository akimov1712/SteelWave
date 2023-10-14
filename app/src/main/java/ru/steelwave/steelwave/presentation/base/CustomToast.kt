package ru.steelwave.steelwave.presentation.base

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import ru.steelwave.steelwave.R

object CustomToast {

    fun toastDefault(context: Context, text: String) {
        val toast = Toast(context)
        val view = LayoutInflater.from(context).inflate(
            R.layout.toast_custom,
            null,
            false
        )
        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        tvTitle.text = text
        toast.view = view
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.TOP, 0, 60)
        toast.show()
    }


}