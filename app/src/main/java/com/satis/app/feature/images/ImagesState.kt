package com.satis.app.feature.images

import android.net.Uri
import android.os.Parcelable
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.PersistState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImagesState(
        @PersistState val photoState: List<PhotoState> = emptyList()
) : MvRxState, Parcelable

@Parcelize
data class PhotoState(val id: String, val thumbnailUrl: Uri, val photoUrl: Uri) : Parcelable