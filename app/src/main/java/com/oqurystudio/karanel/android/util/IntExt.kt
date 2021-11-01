package com.oqurystudio.karanel.android.util

fun Int?.defaultZero(): Int = this ?: 0
fun Int?.defaultMinusOne(): Int = this ?: -1
fun Int?.withDefault(default: Int): Int = this ?: default