package com.satis.app.feature.images

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View

import androidx.recyclerview.widget.RecyclerView

class GridDividerItemDecoration(
        private val horizontalDivider: Drawable,
        private val verticalDivider: Drawable,
        private val columns: Int
) : RecyclerView.ItemDecoration() {

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.drawHorizontalDecoration(canvas)
        parent.drawVerticalDecoration(canvas)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) % columns != 0) {
            outRect.left = horizontalDivider.intrinsicWidth
        }

        if (parent.getChildAdapterPosition(view) >= columns) {
            outRect.top = verticalDivider.intrinsicHeight
        }
    }

    private fun RecyclerView.drawHorizontalDecoration(canvas: Canvas) {
        val rowCount = childCount / columns
        val lastRowChildCount = childCount % columns

        for (i in 1 until columns) {
            val lastRowChildIndex = if (i < lastRowChildCount) {
                i + rowCount * columns
            } else {
                i + (rowCount - 1) * columns
            }

            val firstRowChild = getChildAt(i)
            val lastRowChild = getChildAt(lastRowChildIndex)

            if (firstRowChild != null && lastRowChild != null) {
                horizontalDivider.setBounds(
                        firstRowChild.left - horizontalDivider.intrinsicWidth,
                        firstRowChild.top,
                        firstRowChild.left,
                        lastRowChild.bottom
                )
                horizontalDivider.draw(canvas)
            }
        }
    }

    private fun RecyclerView.drawVerticalDecoration(canvas: Canvas) {
        val childCount = childCount
        val rowCount = childCount / columns

        for (i in 1..rowCount) {
            val rightmostChildIndex = if (i == rowCount) {
                childCount - 1
            } else {
                i * columns + columns - 1
            }

            val leftmostChild = getChildAt(i * columns)
            val rightmostChild = getChildAt(rightmostChildIndex)

            if (leftmostChild != null && rightmostChild != null) {
                verticalDivider.setBounds(
                        leftmostChild.left,
                        leftmostChild.top - verticalDivider.intrinsicHeight,
                        rightmostChild.right,
                        leftmostChild.top
                )
                verticalDivider.draw(canvas)
            }
        }
    }
}