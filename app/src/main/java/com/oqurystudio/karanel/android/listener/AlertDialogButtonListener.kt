package com.oqurystudio.karanel.android.listener

import android.app.Dialog

interface AlertDialogButtonListener {
    fun onPositiveButtonClicked(dialog: Dialog)
    fun onNegativeButtonCLicked(dialog: Dialog)
}