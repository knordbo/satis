package com.satis.app.utils.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Provider

/**
 * Ensures that client creation is deferred to when it is needed, off the main thread.
 */
fun Retrofit.Builder.clientProvider(client: Provider<OkHttpClient>): Retrofit.Builder {
  return callFactory { request -> client.get().newCall(request) }
}

fun OkHttpClient.Builder.addNetworkInterceptors(interceptors: Collection<Interceptor>): OkHttpClient.Builder {
  interceptors.forEach { interceptor ->
    addNetworkInterceptor(interceptor)
  }
  return this
}