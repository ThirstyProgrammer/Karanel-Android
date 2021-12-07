package com.oqurystudio.karanel.android.ui.parents

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.oqurystudio.karanel.android.databinding.ItemParentsBinding
import com.oqurystudio.karanel.android.model.Parents
import com.oqurystudio.karanel.android.util.defaultZero

class ParentsViewHolder constructor(private var mViewBinding: ItemParentsBinding) : RecyclerView.ViewHolder(mViewBinding.root) {

    fun bind(data: Parents.Data) {
        mViewBinding.apply {
            tvName.text = data.motherName
            tvBirthDate.text = data.fatherName
            tvTotalChild.text = data.totalChild.toString()
            bgTotalChildStunting.visibility = if (data.totalChildStunting.defaultZero() > 0) {
                View.VISIBLE
            } else {
                View.GONE
            }
            tvTotalChildStunting.visibility = if (data.totalChildStunting.defaultZero() > 0) {
                View.VISIBLE
            } else {
                View.GONE
            }
            tvTotalChildStunting.text = data.totalChildStunting.toString()
        }
    }
}