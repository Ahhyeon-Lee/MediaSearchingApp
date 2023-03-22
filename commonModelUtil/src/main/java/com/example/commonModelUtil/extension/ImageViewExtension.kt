package com.example.commonModelUtil.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun ImageView.setImage(url: String?, @DrawableRes error: Int? = null) {
    Glide.with(context)
        .load(url)
        .timeout(60000)
        .error(error)
        .into(this)
}

fun ImageView.setImageRound(url: String?, radius: Int, @DrawableRes error: Int? = null) {
    Glide.with(context)
        .load(url)
        .timeout(60000)
        .apply(RequestOptions.bitmapTransform(RoundedCorners(radius)))
        .error(error)
        .into(this)
}