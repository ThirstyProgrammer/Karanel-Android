package com.oqurystudio.karanel.android.ui.parents

import androidx.recyclerview.widget.RecyclerView
import com.oqurystudio.karanel.android.databinding.ItemParentsBinding
import kotlin.random.Random

class ParentsViewHolder constructor(private var mViewBinding: ItemParentsBinding) : RecyclerView.ViewHolder(mViewBinding.root) {

    fun bind(data: String) {
        mViewBinding.apply {
            tvName.text = data
            tvBirthDate.text = "14 April 1994"
            tvTotalChild.text = (0..10).random().toString()
        }
    }
}