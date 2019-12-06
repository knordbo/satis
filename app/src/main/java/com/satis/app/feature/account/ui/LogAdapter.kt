package com.satis.app.feature.account.ui

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.satis.app.R
import com.satis.app.common.logging.LogEntry
import com.satis.app.feature.account.ui.LogAdapter.LogViewHolder
import com.satis.app.utils.view.asyncText
import com.satis.app.utils.view.layoutInflater

class LogAdapter : ListAdapter<LogEntry, LogViewHolder>(Differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder =
            LogViewHolder(parent.layoutInflater.inflate(R.layout.log_item, parent, false) as AppCompatTextView)

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class LogViewHolder(private val view: AppCompatTextView) : RecyclerView.ViewHolder(view) {
        fun bind(log: LogEntry) {
            view.asyncText = log.formatted
        }
    }

    private object Differ : DiffUtil.ItemCallback<LogEntry>() {
        override fun areItemsTheSame(oldItem: LogEntry, newItem: LogEntry): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: LogEntry, newItem: LogEntry): Boolean = oldItem == newItem
    }
}