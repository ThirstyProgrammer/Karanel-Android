package com.oqurystudio.karanel.android.util

import android.content.res.Resources
import kotlin.math.roundToInt

class ViewUtil {

    companion object {
        fun dpToPx(dp: Int): Int {
            val density = Resources.getSystem().displayMetrics.density
            return (dp * density).roundToInt()
        }
    }
}