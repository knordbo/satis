package com.satis.app.feature.images.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Unsplash(val results: List<UnsplashPhoto>)

@Serializable
data class UnsplashPhoto(
  val id: String,
  val urls: Urls,
  val user: User,
  val description: String?
)

@Serializable
data class Urls(
  val regular: String,
  val thumb: String
)

@Serializable
data class User(
  val username: String,
  @SerialName("profile_image") val profileImage: ProfileImage
)

@Serializable
data class ProfileImage(val medium: String)