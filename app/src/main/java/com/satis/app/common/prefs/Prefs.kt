package com.satis.app.common.prefs

interface Prefs {
  val userId: UserId
  var theme: Theme
  var notificationToken: String?
}