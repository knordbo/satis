package com.satis.app.utils.view

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.satis.app.utils.context.requireSystemService

fun Context.hideKeyboard(view: View) {
  requireSystemService<InputMethodManager>().hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboard() {
  requireSystemService<InputMethodManager>().toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}