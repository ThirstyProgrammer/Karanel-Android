package com.oqurystudio.karanel.android.ui.parent

import androidx.recyclerview.widget.RecyclerView
import com.oqurystudio.karanel.android.databinding.ItemChildBinding

class ChildViewHolder constructor(private var mViewBinding: ItemChildBinding) : RecyclerView.ViewHolder(mViewBinding.root) {

    fun bind(data: String) {
        mViewBinding.apply {
            tvName.text = data
            tvAge.text = "4 Tahun 0 Bulan"
        }
    }
}