package com.satis.app.common.prefs

interface Prefs {
  val currentAccountId: String
  var theme: Theme
  var notificationToken: String?
}