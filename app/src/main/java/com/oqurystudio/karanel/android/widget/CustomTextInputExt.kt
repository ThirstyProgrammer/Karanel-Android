package com.oqurystudio.karanel.android.widget

import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.databinding.LayoutTextInputBinding

fun LayoutTextInputBinding.showPassword() {
    etCustom.transformationMethod = null
}

fun LayoutTextInputBinding.hidePassword() {
    etCustom.transformationMethod = PasswordTransformationMethod.getInstance()
}

fun LayoutTextInputBinding.setupEditText(
    title: String,
    hint: String = "",
    indicator: String = "",
    suffixDrawable: Int = 0,
    inputType: Int = InputType.TYPE_CLASS_TEXT
) {
    tvTitle.text = title
    etCustom.apply {
        this.hint = hint
        this.inputType = inputType
    }
    tvIndicator.text = indicator
    if (indicator.isNotBlank()) {
        containerIndicator.visibility = View.VISIBLE
    } else {
        containerIndicator.visibility = View.GONE
    }
    btnSuffix.setImageResource(suffixDrawable)
    if (suffixDrawable > 0) {
        btnSuffix.visibility = View.VISIBLE
    } else {
        btnSuffix.visibility = View.GONE
    }
}

fun LayoutTextInputBinding.setupErrorState(errorMessage: String) {
    containerInputText.setBackgroundResource(R.drawable.bg_rect_rounded_red)
    tvErrorMessage.text = errorMessage
    tvErrorMessage.visibility = View.VISIBLE
}

fun LayoutTextInputBinding.setupNormalState() {
    containerInputText.setBackgroundResource(R.drawable.bg_rect_rounded_grey)
    tvErrorMessage.visibility = View.GONE
}