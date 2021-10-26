package com.oqurystudio.karanel.android.listener

import android.view.View

interface OnItemClickListener {
    fun onItemClicked(v: View, position: Int)
}