package com.satis.app.utils.retrofit

import okhttp3.MediaType
import retrofit2.Retrofit

inline fun <reified T> Retrofit.create(): T = create(T::class.java)

fun jsonMediaType(): MediaType = MediaType.get("application/json; charset=utf-8")