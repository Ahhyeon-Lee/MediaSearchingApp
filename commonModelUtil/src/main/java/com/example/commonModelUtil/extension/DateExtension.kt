package com.example.commonModelUtil.extension

import java.text.SimpleDateFormat
import java.util.regex.Pattern

val pattern1 = "yyyy-MM-ddTHH:mm:ss+09:00"
val pattern2 = "yyyy-MM-dd"

fun String.convertDateString(pattern: String = pattern2, originPattern:String = pattern1): String {
    val formatter = SimpleDateFormat(originPattern)
    val date = formatter.parse(this)
    val newFormatter = SimpleDateFormat(pattern)
    return newFormatter.format(date)
}