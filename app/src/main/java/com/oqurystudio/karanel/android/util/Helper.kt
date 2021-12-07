package com.oqurystudio.karanel.android.util

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class Helper {

    companion object {

        fun getChildAge(d1: String): String {
            return try {
                val date: Date? = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(d1)
                val months = monthsBetween(date)
                getWording(months, weeksBetween(date))
            } catch (e: Exception) {
                ""
            }
        }

        private fun monthsBetween(d1: Date?): Int {
            if (d1 != null) {
                val calendar = Calendar.getInstance()
                val month2 = 12 * calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH)
                calendar.time = d1
                val month1 = 12 * calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH)
                return abs(month2 - month1)
            }
            return 0
        }

        private fun weeksBetween(d1: Date?): Int {
            if (d1 != null) {
                val calendar = Calendar.getInstance()
                val week2 = calendar.get(Calendar.WEEK_OF_MONTH)
                calendar.time = d1
                val week1 = calendar.get(Calendar.WEEK_OF_MONTH)
                return abs(week2 - week1)
            }
            return 0
        }

        private fun getWording(months: Int, weeks: Int): String {
            return when {
                months <= 0 -> {
                    "$weeks Minggu"
                }
                else -> {
                    "$months Bulan"
                }
            }
        }
    }
}