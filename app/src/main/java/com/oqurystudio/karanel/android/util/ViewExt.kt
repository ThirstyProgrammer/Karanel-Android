package com.oqurystudio.karanel.android.util

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

fun View.setOnSafeClickListener(onSafeClick: (View?) -> Unit) {
    setOnClickListener(SafeClickListener { v -> onSafeClick(v) })
}

fun View.setOnSafeClickListener(interval: Int, onSafeClick: (View?) -> Unit) {
    setOnClickListener(SafeClickListener(interval) { v -> onSafeClick(v) })
}

fun View.setViewVisibility(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

fun View.setViewVisibility(show: Boolean, isGone: Boolean) {
    visibility = if (show) View.VISIBLE else if (isGone) View.GONE else View.INVISIBLE
}

fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
    isFocusableInTouchMode = false
    isClickable = true
    isFocusable = false

    val myCalendar = Calendar.getInstance()
    val datePickerOnDataSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat(format, Locale.ENGLISH)
            setText(sdf.format(myCalendar.time))
        }

    setOnClickListener {
        DatePickerDialog(
            context, datePickerOnDataSetListener, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).run {
            maxDate?.time?.also { datePicker.maxDate = it }
            show()
        }
    }
}