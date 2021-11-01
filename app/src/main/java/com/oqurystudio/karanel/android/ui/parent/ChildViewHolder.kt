package com.oqurystudio.karanel.android.ui.parent

import androidx.recyclerview.widget.RecyclerView
import com.oqurystudio.karanel.android.databinding.ItemChildBinding
import com.oqurystudio.karanel.android.model.Parent

class ChildViewHolder constructor(private var mViewBinding: ItemChildBinding) : RecyclerView.ViewHolder(mViewBinding.root) {

    fun bind(data: Parent.Child) {
        mViewBinding.apply {
            tvName.text = data.name
            tvAge.text = "4 Tahun 0 Bulan"
        }
    }
}