package com.oqurystudio.karanel.android.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val spaceHeight: Int, private val rvType: RvType, private val isFullMargin: Boolean) :
    RecyclerView.ItemDecoration() {

    enum class RvType {
        RV_HORIZONTAL,
        RV_VERTICAL,
        RV_VERTICAL_GRID
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        with(outRect) {
            when (rvType) {
                RvType.RV_HORIZONTAL -> {
                    left = when {
                        parent.getChildAdapterPosition(view) == 0 -> if (isFullMargin) spaceHeight else 0
                        else -> spaceHeight
                    }
                    right = spaceHeight
                }
                RvType.RV_VERTICAL -> {
                    top = when {
                        parent.getChildAdapterPosition(view) == 0 -> if (isFullMargin) (spaceHeight * 2) else 0
                        else -> spaceHeight
                    }
                    bottom = spaceHeight
                }
                RvType.RV_VERTICAL_GRID -> {
                    when (parent.getChildAdapterPosition(view) % 2) {
                        0 -> {
                            left = spaceHeight * 2
                            right = spaceHeight
                        }
                        1 -> {
                            left = spaceHeight
                            right = spaceHeight * 2
                        }
                    }
                }
                else -> {
                }
            }
        }
    }
}