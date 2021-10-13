package com.base.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.*
import androidx.core.content.res.ResourcesCompat

enum class FontWeight{
    Medium,Bold,Light
}
@ColorInt
inline fun Context.getColorCompat(@ColorRes color:Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        getColor(color)
    }else
        resources.getColor(color)
}


inline fun Context.getDrawableCompat(@DrawableRes drawableRes: Int): Drawable? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        ResourcesCompat.getDrawable(resources, drawableRes, theme)
    }else
        resources.getDrawable(drawableRes)
}
