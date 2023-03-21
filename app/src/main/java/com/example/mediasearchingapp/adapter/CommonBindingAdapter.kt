package com.example.mediasearchingapp.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.commonModelUtil.extension.setImage

object CommonBindingAdapter {

    @BindingAdapter("clipToOutLine")
    @JvmStatic
    fun clipToOutLine(view: View, clipToOutLine: Boolean) {
        view.clipToOutline = clipToOutLine
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun imageUrl(view: ImageView, url: String) {
        view.setImage(url)
    }
}