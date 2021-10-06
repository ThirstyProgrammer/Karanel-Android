package com.oqurystudio.karanel.android.util

import android.os.SystemClock
import android.view.View

/**
 * https://medium.com/@roman.bjit/kotlin-expert-safe-click-listener-in-android-539243f2031e
 */
class SafeClickListener(
    private val interval: Int = 1000,
    private val onSafeClick: (View?) -> Unit
): View.OnClickListener {

    private var lastClickTime: Long = 0L

    override fun onClick(v: View?) {
        if (SystemClock.elapsedRealtime() - lastClickTime < interval) {
            return
        }
        lastClickTime = SystemClock.elapsedRealtime()
        onSafeClick(v)
    }
}