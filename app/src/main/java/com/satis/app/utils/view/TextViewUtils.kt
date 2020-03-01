package com.satis.app.utils.view

import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat

var AppCompatTextView.asyncText: CharSequence
  get() = text
  set(value) {
    setTextFuture(PrecomputedTextCompat.getTextFuture(value, TextViewCompat.getTextMetricsParams(this), null))
  }