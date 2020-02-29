package com.satis.app.feature.account.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.satis.app.feature.account.ui.DateHeaderViewBinder.DateHeaderViewHolder
import com.satis.app.feature.account.ui.LogViewBinder.LogViewHolder

class LogAdapter : ListAdapter<LogAdapterItem, RecyclerView.ViewHolder>(Differ) {

  companion object {
    private const val VIEW_TYPE_LOG_ENTRY_ITEM_VIEW_TYPE = 1
    private const val VIEW_TYPE_DATE_HEADER_ITEM_VIEW_TYPE = 2
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
      when (viewType) {
        VIEW_TYPE_LOG_ENTRY_ITEM_VIEW_TYPE -> LogViewBinder.createViewHolder(parent)
        VIEW_TYPE_DATE_HEADER_ITEM_VIEW_TYPE -> DateHeaderViewBinder.createViewHolder(parent)
        else -> throw RuntimeException("uh oh!")
      }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
      when (val item = getItem(position)) {
        is LogEntryAdapterItem -> (holder as LogViewHolder).bind(item)
        is DateHeaderAdapterItem -> (holder as DateHeaderViewHolder).bind(item)
      }

  override fun getItemViewType(position: Int): Int =
      when (getItem(position)) {
        is LogEntryAdapterItem -> VIEW_TYPE_LOG_ENTRY_ITEM_VIEW_TYPE
        is DateHeaderAdapterItem -> VIEW_TYPE_DATE_HEADER_ITEM_VIEW_TYPE
      }

  private object Differ : DiffUtil.ItemCallback<LogAdapterItem>() {
    override fun areItemsTheSame(oldItem: LogAdapterItem, newItem: LogAdapterItem): Boolean =
        oldItem::class == newItem::class && oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: LogAdapterItem, newItem: LogAdapterItem): Boolean =
        oldItem::class == newItem::class && oldItem == newItem
  }
}