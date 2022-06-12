package com.satis.app.feature.account

import android.os.Parcelable
import com.satis.app.common.logging.LogEntry
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccountState(
  val buildData: BuildData? = null,
  val logs: List<LogEntry> = emptyList(),
  val notificationToken: String? = null,
) : Parcelable

@Parcelize
data class BuildData(
  val versionNum: Long,
  val buildTime: Long,
) : Parcelable