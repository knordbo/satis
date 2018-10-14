package com.satis.app.feature.account

import android.os.Parcelable
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.PersistState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AccountState(
        @PersistState val data: AccountData? = null,
        @PersistState val showLog: Boolean = false
) : MvRxState, Parcelable

@Parcelize
data class AccountData(
        val versionNum: Int,
        val buildTime: Long,
        val log: String
) : Parcelable