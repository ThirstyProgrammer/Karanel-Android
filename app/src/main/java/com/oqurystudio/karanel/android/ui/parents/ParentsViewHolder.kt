package com.oqurystudio.karanel.android.ui.parents

import androidx.recyclerview.widget.RecyclerView
import com.oqurystudio.karanel.android.databinding.ItemParentsBinding
import com.oqurystudio.karanel.android.model.Parents
import kotlin.random.Random

class ParentsViewHolder constructor(private var mViewBinding: ItemParentsBinding) : RecyclerView.ViewHolder(mViewBinding.root) {

    fun bind(data: Parents.Data) {
        mViewBinding.apply {
            tvName.text = data.motherName
            tvBirthDate.text = data.fatherName
            tvTotalChild.text = data.totalChild.toString()
        }
    }
}