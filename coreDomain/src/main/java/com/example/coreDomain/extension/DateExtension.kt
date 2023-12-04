package com.example.coreDomain.extension

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

fun Int.toTimeFormat(): String {
    val hour = this / (60 * 60) % 24
    val minute = (this / 60) % 60
    val second = this % 60
    return if (hour > 0) String.format("%02d : %02d : %02d", hour, minute, second)
    else String.format("%02d : %02d", minute, second)
}