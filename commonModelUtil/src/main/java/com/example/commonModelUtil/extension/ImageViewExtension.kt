package com.example.commonModelUtil.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun ImageView.setImage(
    url: String?,
    @DrawableRes errorRes: Int? = null,
    @DrawableRes placeholderRes: Int? = null
) {
    Glide.with(context)
        .load(url)
        .timeout(60000)
        .apply {
            placeholderRes?.let { placeholder(it) }
            errorRes?.let { error(errorRes) }
        }
        .into(this)
}