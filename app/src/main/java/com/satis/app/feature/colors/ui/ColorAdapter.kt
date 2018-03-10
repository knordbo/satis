package com.satis.app.feature.colors.ui

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ColorViewHolder(ColorItemView(parent.context))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.colorItemView.bind(items[position])
    }

    class ColorViewHolder(val colorItemView: ColorItemView) : RecyclerView.ViewHolder(colorItemView)

}