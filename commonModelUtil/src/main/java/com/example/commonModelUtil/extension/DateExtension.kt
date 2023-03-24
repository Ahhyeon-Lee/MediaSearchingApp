package com.example.commonModelUtil.extension

import java.text.SimpleDateFormat
import java.util.*

const val pattern1 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
const val pattern2 = "yyyy-MM-dd HH:mm"

fun String.convertDateString(pattern: String = pattern2, originPattern:String = pattern1): String {
    val formatter = SimpleDateFormat(originPattern, Locale.getDefault())
    val date = formatter.parse(this)
    val newFormatter = SimpleDateFormat(pattern, Locale.getDefault())
    return date?.let { newFormatter.format(it) } ?: ""
}

fun String.getDate(pattern:String = pattern1): Date? {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.parse(this)
}