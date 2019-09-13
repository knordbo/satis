package com.satis.app.utils.network

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import javax.inject.Provider

/**
 * Ensures that client creation is deferred to when it is needed, off the main thread.
 */
fun Retrofit.Builder.client(client: Provider<OkHttpClient>): Retrofit.Builder {
    return callFactory(object : Call.Factory {
        override fun newCall(request: Request) = client.get().newCall(request)
    })
}