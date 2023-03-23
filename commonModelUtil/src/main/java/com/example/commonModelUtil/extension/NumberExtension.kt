package com.example.commonModelUtil.extension

import android.content.Context
import android.util.DisplayMetrics

fun Number.toPx(context: Context): Int {
    val densityDpi = context.resources.displayMetrics.densityDpi
    return (this.toFloat() * (densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

fun Number.toDp(context: Context): Float {
    val densityDpi = context.resources.displayMetrics.densityDpi
    return this.toFloat() / (densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun Int.toTimeFormat(): String {
    val second = this % 60
    val minute = this / 60
    val hour = this / 60 / 60
    return if (hour > 0) String.format("%02d : %02d : %02d", hour, minute, second)
    else String.format("%02d : %02d", minute, second)
}