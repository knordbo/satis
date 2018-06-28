package com.satis.app.utils.view

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.hideKeyboard(view: View) {
    getSystemService(InputMethodManager::class.java).hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboard() {
    getSystemService(InputMethodManager::class.java).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}