package com.thanh.date_between.common

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

class NormalizeHelper {

    companion object{
        @JvmStatic
        fun convertDpToPixel(dp: Int): Int {
            val displayMetrics: DisplayMetrics = com.thanh.date_between.common.resources.Resources.getResources().displayMetrics
            return Math.round(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
                    displayMetrics
                )
            )
        }

        @JvmStatic
        fun convertDpToPixel(resources: Resources, dp: Int): Int {
            val displayMetrics = resources.displayMetrics
            return Math.round(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
                    displayMetrics
                )
            )
        }

        @JvmStatic
        fun convertPixelsToDp(px: Int): Int {
            val metrics: DisplayMetrics = com.thanh.date_between.common.resources.Resources.getResources().displayMetrics
            return px / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
        }
    }

}