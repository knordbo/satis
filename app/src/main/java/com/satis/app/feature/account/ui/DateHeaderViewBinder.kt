package com.satis.app.feature.account.ui

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.satis.app.R
import com.satis.app.utils.view.asyncText
import com.satis.app.utils.view.layoutInflater

object DateHeaderViewBinder {

  @JvmStatic
  fun createViewHolder(parent: ViewGroup) =
    DateHeaderViewHolder(parent.layoutInflater.inflate(R.layout.header_item, parent, false) as AppCompatTextView)

  class DateHeaderViewHolder(private val view: AppCompatTextView) : RecyclerView.ViewHolder(view) {
    fun bind(header: String) {
      view.asyncText = header
    }
  }

}