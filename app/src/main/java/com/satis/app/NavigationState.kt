package com.satis.app

import android.os.Parcelable
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.PersistState
import com.satis.app.Tab.HOME
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NavigationState(
        @PersistState val currentTab: Tab = HOME,
        val reselectedTab: Tab? = null
) : MvRxState, Parcelable

@Parcelize
enum class Tab : Parcelable {
    HOME,
    IMAGES,
    ACCOUNT
}