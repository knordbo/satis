package com.satis.app.feature.images

import android.os.Parcelable
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.PersistState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImagesState(
        @PersistState val flickerPhotoUrls: List<PhotoState> = emptyList()
) : MvRxState, Parcelable

@Parcelize
data class PhotoState(val id: String, val url: String) : Parcelable