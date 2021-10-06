package com.oqurystudio.karanel.android.util

import android.view.View

fun View.setOnSafeClickListener(onSafeClick: (View?) -> Unit) {
    setOnClickListener(SafeClickListener { v -> onSafeClick(v) })
}

fun View.setOnSafeClickListener(interval: Int, onSafeClick: (View?) -> Unit) {
    setOnClickListener(SafeClickListener(interval) { v -> onSafeClick(v) })
}