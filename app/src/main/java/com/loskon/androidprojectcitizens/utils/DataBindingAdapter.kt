package com.loskon.androidprojectcitizens.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("placeImage")
fun ImageView.bindSrcCompat(drawId: Drawable) {
    setImageDrawable(drawId)
}

