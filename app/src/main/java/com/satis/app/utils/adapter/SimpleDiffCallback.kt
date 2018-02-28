package com.satis.app.utils.adapter

import android.support.v7.util.DiffUtil

class SimpleDiffCallback<in T>(
        private val oldList: List<T>,
        private val newList: List<T>,
        private val areItemsTheSame: (T, T) -> Boolean,
        private val areContentsTheSame: (T, T) -> Boolean
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            areItemsTheSame(oldList[oldItemPosition], newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            areContentsTheSame(oldList[oldItemPosition], newList[newItemPosition])
}