package com.oqurystudio.karanel.android.util

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.makeToast(string: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireContext(), string, Toast.LENGTH_LONG).show()
}