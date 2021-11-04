package com.miko.moviedb.ext.reusable

import android.graphics.Rect
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

class LinearLayoutItemDecoration(
    private val spacing: Int,
    private val edgeSpacing: Int = 0,
    private val orientation: Int = RecyclerView.VERTICAL,
    private val includeEdge: Boolean = false,
): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        outRect.apply {
            if (orientation == LinearLayout.VERTICAL) {
                if (includeEdge) {
                    when (position) {
                        0 -> {
                            top = edgeSpacing
                        }
                        parent.adapter?.itemCount?.minus(1) -> {
                            top = spacing
                            bottom = edgeSpacing
                        }
                        else -> {
                            top = spacing
                        }
                    }
                } else {
                    if (position > 0) {
                        top = spacing
                    }
                }
            } else {
                if (includeEdge) {
                    when (position) {
                        0 -> {
                            left = edgeSpacing
                        }
                        parent.adapter?.itemCount?.minus(1) -> {
                            left = spacing
                            right = edgeSpacing
                        }
                        else -> {
                            left = spacing
                        }
                    }
                    top = edgeSpacing
                } else {
                    if (position > 0) {
                        left = spacing
                    }
                }
            }
        }

    }
}