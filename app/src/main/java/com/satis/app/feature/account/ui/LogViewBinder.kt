package com.satis.app.feature.account.ui

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.satis.app.R
import com.satis.app.utils.view.asyncText
import com.satis.app.utils.view.layoutInflater

object LogViewBinder {

  @JvmStatic
  fun createViewHolder(parent: ViewGroup) =
    LogViewHolder(parent.layoutInflater.inflate(R.layout.log_item, parent, false) as AppCompatTextView)

  class LogViewHolder(private val view: AppCompatTextView) : RecyclerView.ViewHolder(view) {
    fun bind(message: String) {
      view.asyncText = message
    }
  }

}