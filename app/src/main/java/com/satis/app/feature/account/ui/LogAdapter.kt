package com.satis.app.feature.account.ui

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.satis.app.R
import com.satis.app.common.logging.LogEntry
import com.satis.app.feature.account.ui.LogAdapter.LogViewHolder
import com.satis.app.utils.view.layoutInflater
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LogAdapter : ListAdapter<LogEntry, LogViewHolder>(object : DiffUtil.ItemCallback<LogEntry>() {
    override fun areItemsTheSame(oldItem: LogEntry, newItem: LogEntry): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: LogEntry, newItem: LogEntry): Boolean = oldItem == newItem

}) {

    private val dateFormatter by lazy { SimpleDateFormat("dd.MM.YY HH:mm", Locale.US) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder =
            LogViewHolder(parent.layoutInflater.inflate(R.layout.log_item, parent, false) as AppCompatTextView)

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val log = getItem(position)
        val time = dateFormatter.format(Date(log.timestamp))
        val line = "[$time] [${log.tag}] ${log.message}"
        holder.logView.text = line
    }

    class LogViewHolder(val logView: AppCompatTextView) : RecyclerView.ViewHolder(logView)
}