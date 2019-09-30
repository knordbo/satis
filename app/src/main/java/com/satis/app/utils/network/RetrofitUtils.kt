package com.satis.app.utils.network

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType

fun jsonMediaType(): MediaType = "application/json; charset=utf-8".toMediaType()