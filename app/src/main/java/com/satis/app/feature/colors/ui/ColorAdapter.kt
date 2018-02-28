package com.satis.app.feature.colors.ui

import android.graphics.Color
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.view.setPadding
import com.satis.app.R
import com.satis.app.feature.colors.persistence.ColorEntity
import com.satis.app.utils.adapter.SimpleDiffCallback

class ColorAdapter : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    private var items: List<ColorEntity> = emptyList()

    fun bind(newItems: List<ColorEntity>) {
        val oldItems = items
        items = newItems

        SimpleDiffCallback(
                oldList = oldItems,
                newList = newItems,
                areContentsTheSame = { lhs, rhs -> lhs.color == rhs.color },
                areItemsTheSame = { lhs, rhs -> lhs.id == rhs.id }
        ).let {
            DiffUtil.calculateDiff(it).dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ColorViewHolder(TextView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                setPadding(context.resources.getDimension(R.dimen.key_line).toInt())
            })

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        (holder.itemView as? TextView)?.apply {
            with(items.get(position)) {
                text = color
                try {
                    setBackgroundColor(Color.parseColor(color))
                } catch (t: Throwable) {
                    setBackgroundColor(0)
                }
            }
        }
    }

    class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}