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