package com.example.mediasearchingapp.extension

import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

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

fun EditText.textChangesFlow() = callbackFlow {
    val listener = addTextChangedListener {
        trySend(it.toString())
    }
    awaitClose {
        removeTextChangedListener(listener)
    }
}