package com.satis.app.feature.images

import android.net.Uri
import android.os.Parcelable
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.PersistState
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImagesState(
  @PersistState val photoState: List<PhotoState> = emptyList()
) : MavericksState, Parcelable

@Parcelize
data class PhotoState(
  val id: String,
  val thumbnailUrl: Uri,
  val photoUrl: Uri,
  val user: User,
  val description: String?
) : Parcelable

@Parcelize
data class User(
  val username: String,
  val userAvatar: Uri
) : Parcelable