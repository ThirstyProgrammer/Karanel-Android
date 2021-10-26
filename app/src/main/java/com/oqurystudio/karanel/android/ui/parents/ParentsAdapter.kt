package com.oqurystudio.karanel.android.ui.parents

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oqurystudio.karanel.android.databinding.ItemParentsBinding
import com.oqurystudio.karanel.android.listener.OnItemClickListener

class ParentsAdapter : RecyclerView.Adapter<ParentsViewHolder>() {

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_LOADING = 1
    }

    private lateinit var mItemClickListener: OnItemClickListener
    private var items: ArrayList<String> = arrayListOf()
    private var isLoading: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentsViewHolder {
        val mViewBinding: ItemParentsBinding = ItemParentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return when (viewType) {
            TYPE_ITEM -> ParentsViewHolder(mViewBinding)
            else -> ParentsViewHolder(mViewBinding)
        }
    }

    override fun onBindViewHolder(holder: ParentsViewHolder, position: Int) {
        when (holder) {
            is ParentsViewHolder -> {
                holder.bind(items[position])
                holder.itemView.setOnClickListener {
                    mItemClickListener.onItemClicked(it, position)
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size
    override fun getItemViewType(position: Int): Int = TYPE_ITEM
    //    override fun getItemViewType(position: Int): Int = items[position].typeItem

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mItemClickListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<String>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<String>, isNextPageAvailable: Boolean) {
        val itemsSizeBeforeAdded = items.size
        if (items.isNullOrEmpty()) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        } else {
            if (data.size > items.size) {
                val currentPosition = items.size
                removeFooter()
                items.addAll(data.subList(items.size, data.size - 1))
                notifyItemInserted(currentPosition)
            }
        }
        if (data.size != itemsSizeBeforeAdded && isNextPageAvailable) addFooter()
    }

    private fun addFooter() {
        isLoading = true
//        items.add(User(typeItem = TYPE_LOADING))
        notifyItemInserted(items.size)
    }

    private fun removeFooter() {
        isLoading = false
        if (!items.isNullOrEmpty()) {
            items.removeAt(items.size - 1)
        }
        notifyItemRemoved(items.size)
    }
}