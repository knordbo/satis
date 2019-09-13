package com.satis.app.utils.network

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

inline fun <reified T> Retrofit.create(): T = create(T::class.java)

fun jsonMediaType(): MediaType = "application/json; charset=utf-8".toMediaType()