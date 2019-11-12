package com.satis.app.utils.network

import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import javax.inject.Provider

/**
 * Ensures that client creation is deferred to when it is needed, off the main thread.
 */
fun Retrofit.Builder.clientProvider(client: Provider<OkHttpClient>): Retrofit.Builder {
    return callFactory(object : Call.Factory {
        override fun newCall(request: Request) = client.get().newCall(request)
    })
}

fun OkHttpClient.Builder.addNetworkInterceptors(interceptors: Collection<Interceptor>): OkHttpClient.Builder {
    interceptors.forEach { interceptor ->
        addNetworkInterceptor(interceptor)
    }
    return this
}