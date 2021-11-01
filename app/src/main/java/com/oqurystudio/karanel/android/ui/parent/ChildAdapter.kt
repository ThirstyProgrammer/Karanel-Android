package com.oqurystudio.karanel.android.ui.parent

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oqurystudio.karanel.android.databinding.ItemChildBinding
import com.oqurystudio.karanel.android.listener.OnItemClickListener
import com.oqurystudio.karanel.android.model.Parent
import com.oqurystudio.karanel.android.ui.parents.ParentsAdapter

class ChildAdapter : RecyclerView.Adapter<ChildViewHolder>() {

    private lateinit var mItemClickListener: OnItemClickListener
    private var items: ArrayList<Parent.Child> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val mViewBinding: ItemChildBinding = ItemChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChildViewHolder(mViewBinding)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClicked(it, position)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mItemClickListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Parent.Child>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }
}