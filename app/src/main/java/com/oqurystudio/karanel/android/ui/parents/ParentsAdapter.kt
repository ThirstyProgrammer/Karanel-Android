package com.oqurystudio.karanel.android.ui.parents

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oqurystudio.karanel.android.databinding.ItemParentsBinding
import com.oqurystudio.karanel.android.listener.OnItemClickListener
import com.oqurystudio.karanel.android.model.Parents

class ParentsAdapter : RecyclerView.Adapter<ParentsViewHolder>() {

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_LOADING = 1
    }

    private lateinit var mItemClickListener: OnItemClickListener
    private var items: ArrayList<Parents.Data> = arrayListOf()
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
    override fun getItemViewType(position: Int): Int = items[position].typeItem

    fun getItem(position: Int): Parents.Data = items[position]

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mItemClickListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<Parents.Data>, isNextPageAvailable: Boolean, isAppend: Boolean) {
        val itemsSizeBeforeAdded = items.size
        if (isAppend) {
            val currentPosition = items.size
            removeFooter()
            items.addAll(data)
            notifyItemInserted(currentPosition)
        } else {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
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