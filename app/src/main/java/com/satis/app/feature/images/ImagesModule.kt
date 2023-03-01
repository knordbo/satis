package com.satis.app.feature.images

import com.satis.app.AccountDatabase
import com.satis.app.common.annotations.Background
import com.satis.app.di.account.AccountScope
import com.satis.app.feature.images.data.UnsplashApi
import com.satis.app.feature.images.data.UnsplashRepository
import com.satis.app.feature.images.data.UnsplashRepositoryImpl
import com.satis.app.feature.images.data.db.UnsplashQueries
import com.satis.app.feature.images.startup.UnsplashPreloadTask
import com.satis.app.startup.StartupTask
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.migration.DisableInstallInCheck
import dagger.multibindings.IntoSet
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module(includes = [ImagesAccountBindingModule::class])
@DisableInstallInCheck
object ImagesAccountModule {

  @Provides
  fun provideUnsplashQueries(database: AccountDatabase): UnsplashQueries = database.unsplashQueries
}

@Module
@DisableInstallInCheck
abstract class ImagesAccountBindingModule {

  @Binds
  @AccountScope
  abstract fun provideUnsplashRepository(bind: UnsplashRepositoryImpl): UnsplashRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ImagesSingletonModule {

  @Provides
  @Singleton
  fun provideUnsplashApi(retrofit: Retrofit): UnsplashApi = retrofit.create()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ImagesSingletonBindingModule {

  @Binds
  @IntoSet
  @Background
  abstract fun provideUnsplashTask(bind: UnsplashPreloadTask): StartupTask
}