package com.oqurystudio.karanel.android.widget

import android.view.View
import android.widget.ArrayAdapter
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.databinding.LayoutSpinnerBinding

fun LayoutSpinnerBinding.setupSpinner(title: String, adapter: ArrayAdapter<String>) {
    spCustom.adapter = adapter
    tvTitle.text = title
    btnSuffix.setOnClickListener {
        spCustom.performClick()
    }
}

fun LayoutSpinnerBinding.setupErrorState(errorMessage: String) {
    containerSpinner.setBackgroundResource(R.drawable.bg_rect_rounded_red)
    tvErrorMessage.text = errorMessage
    tvErrorMessage.visibility = View.VISIBLE
}

fun LayoutSpinnerBinding.setupNormalState() {
    containerSpinner.setBackgroundResource(R.drawable.bg_rect_rounded_grey)
    tvErrorMessage.visibility = View.GONE
}