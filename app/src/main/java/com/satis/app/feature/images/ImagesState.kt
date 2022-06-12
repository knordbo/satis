package com.satis.app.feature.images

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ImagesState(
  val photoState: List<PhotoState> = emptyList(),
  val scrollEvent: ScrollEvent = ScrollEvent.None,
)

@Parcelize
data class PhotoState(
  val id: String,
  val thumbnailUrl: Uri,
  val photoUrl: Uri,
  val user: User,
  val description: String?,
) : Parcelable

@Parcelize
data class User(
  val username: String,
  val userAvatar: Uri,
) : Parcelable

sealed class ScrollEvent {
  object None : ScrollEvent()
  object ScrollToTop : ScrollEvent()
}