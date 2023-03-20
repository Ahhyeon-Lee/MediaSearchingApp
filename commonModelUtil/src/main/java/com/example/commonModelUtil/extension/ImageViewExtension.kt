package com.example.commonModelUtil.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun ImageView.setImage(url: String?, @DrawableRes error: Int? = null) {
    Glide.with(context)
        .load(url)
        .timeout(60000)
        .error(error)
        .into(this)
}