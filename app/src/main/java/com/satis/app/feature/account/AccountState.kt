package com.satis.app.feature.account

import android.os.Parcelable
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.PersistState
import com.satis.app.common.logging.LogEntry
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccountState(
  @PersistState val buildData: BuildData? = null,
  @PersistState val logs: List<LogEntry> = emptyList(),
  @PersistState val notificationToken: String? = null,
) : MvRxState, Parcelable

@Parcelize
data class BuildData(
  val versionNum: Long,
  val buildTime: Long
) : Parcelable