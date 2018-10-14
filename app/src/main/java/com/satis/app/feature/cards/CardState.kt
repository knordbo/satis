package com.satis.app.feature.cards

import android.os.Parcelable
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.PersistState
import com.satis.app.feature.cards.data.Card
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardState(
        @PersistState val cards: List<Card> = emptyList()
) : MvRxState, Parcelable