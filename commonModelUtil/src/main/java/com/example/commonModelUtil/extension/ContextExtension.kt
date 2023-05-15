package com.example.commonModelUtil.extension

import android.content.Context
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.annotation.StringRes

inline val Context.layoutInflater: android.view.LayoutInflater
    get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as android.view.LayoutInflater

fun Context.showToast(@StringRes id: Int) {
    Toast.makeText(this, getString(id), Toast.LENGTH_SHORT).show()
}

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.getDimenInt(@DimenRes id: Int) = resources.getDimension(id).toInt()