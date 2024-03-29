package com.satis.app.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.satis.app.utils.network.addNetworkInterceptors
import com.satis.app.utils.network.clientProvider
import com.satis.app.utils.network.jsonMediaType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.Multibinds
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Provider
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

  @Provides
  @Singleton
  fun provideJson(): Json = Json {
    isLenient = true
    ignoreUnknownKeys = true
    allowSpecialFloatingPointValues = true
    useArrayPolymorphism = true
  }

  @Provides
  @Singleton
  fun provideOkHttp(interceptors: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient = OkHttpClient.Builder()
    .addNetworkInterceptors(interceptors)
    .build()

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: Provider<OkHttpClient>, json: Json): Retrofit = Retrofit.Builder()
    .baseUrl("https://dummy.com/")
    .clientProvider(okHttpClient)
    .addConverterFactory(json.asConverterFactory(jsonMediaType()))
    .build()

}

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkBindingModule {

  @Multibinds
  abstract fun provideOkHttpInterceptors(): Set<Interceptor>

}