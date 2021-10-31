package com.oqurystudio.karanel.android.util

import android.util.Patterns
import java.util.regex.Pattern

class RegexUtil {

    companion object {
        val EMAIL_PATTERN = Patterns.EMAIL_ADDRESS

        fun checkRegex(string: String, pattern: Pattern): Boolean {
            return pattern.matcher(string).matches()
        }

        fun checkRegex(string: String, pattern: String): Boolean {
            val pattern = Pattern.compile(pattern)
            return pattern.matcher(string).matches()
        }
    }
}