package com.satis.app.feature.cards

import android.os.Parcelable
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.PersistState
import com.airbnb.mvrx.Uninitialized
import com.satis.app.feature.cards.data.Card
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class CardState(
    @PersistState val cards: List<Card> = emptyList(),
    @PersistState val creatingCard: Card = Card(title = "", message = ""),
    val creatingCardAsync: @RawValue Async<Card> = Uninitialized
) : MvRxState, Parcelable