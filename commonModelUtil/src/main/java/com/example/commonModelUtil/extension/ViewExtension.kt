package com.example.commonModelUtil.extension

import android.view.View

fun View.visible(visible: Boolean = true): Boolean {
    visibility = if (visible) View.VISIBLE else View.GONE
    return visible
}