package com.oqurystudio.karanel.android.widget

import android.text.InputType
import android.text.method.PasswordTransformationMethod
import com.oqurystudio.karanel.android.databinding.LayoutTextInputBinding

fun LayoutTextInputBinding.showPassword() {
    etCustom.transformationMethod = null
}

fun LayoutTextInputBinding.hidePassword() {
    etCustom.transformationMethod = PasswordTransformationMethod.getInstance()
}

fun LayoutTextInputBinding.setupEditText(title: String, hint: String = "", inputType: Int = InputType.TYPE_CLASS_TEXT) {
    tvTitle.text = title
    etCustom.apply {
        this.hint = hint
        this.inputType = inputType
    }
}