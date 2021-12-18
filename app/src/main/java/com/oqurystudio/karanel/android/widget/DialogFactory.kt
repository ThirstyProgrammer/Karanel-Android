package com.oqurystudio.karanel.android.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.text.color
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.listener.AlertDialogButtonListener
import com.oqurystudio.karanel.android.util.setOnSafeClickListener

class DialogFactory {
    companion object {

        fun createDialogCodeTracking(
            context: Context,
            code: String,
            dialogButtonListener: AlertDialogButtonListener? = null
        ): Dialog {
            val builder = AlertDialog.Builder(context)
            val dialog = builder.create()
            dialog.setCancelable(false)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.dialog_code_tracking, null)
            val tvCode: AppCompatTextView = view.findViewById(R.id.tv_code)
            val btnDownload: Button = view.findViewById(R.id.btn_download)
            tvCode.text = code
            btnDownload.setOnSafeClickListener { dialogButtonListener?.onPositiveButtonClicked(dialog) }

            dialog.setView(view)
            return dialog
        }

        fun createDialogStunting(
            context: Context,
            chatNumber: String,
            dialogButtonListener: AlertDialogButtonListener? = null
        ): Dialog {
            val builder = AlertDialog.Builder(context)
            val dialog = builder.create()
            dialog.setCancelable(false)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.dialog_stunting, null)
            val tvTitle: AppCompatTextView = view.findViewById(R.id.tv_title)
            val btnChat: Button = view.findViewById(R.id.btn_chat)
            val btnClose: Button = view.findViewById(R.id.btn_close)
            val spannableBuilder = SpannableStringBuilder()
                .append("Anak anda saat ini statusnya ")
                .color(ContextCompat.getColor(context, R.color.red_text)) {
                    append("stunting.")
                }
            tvTitle.text = spannableBuilder
            btnChat.text = chatNumber
            btnChat.setOnSafeClickListener { dialogButtonListener?.onPositiveButtonClicked(dialog) }
            btnClose.setOnSafeClickListener { dialog.dismiss() }
            dialog.setView(view)
            return dialog
        }
    }
}