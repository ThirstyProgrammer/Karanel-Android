package com.oqurystudio.karanel.android.util

fun String?.defaultDash(): String = if (isNullOrEmpty()) "-" else this
fun String?.defaultEmpty(): String = if (isNullOrEmpty()) "" else this