package com.thanh.date_between

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics


fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun getScreenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}

fun convertDpToPixel(dp: Float, context: Context): Float {
    return dp * (context.resources
        .displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}