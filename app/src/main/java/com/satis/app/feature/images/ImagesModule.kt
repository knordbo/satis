package com.satis.app.feature.images

import com.satis.app.Database
import com.satis.app.common.annotations.Background
import com.satis.app.feature.images.data.UnsplashApi
import com.satis.app.feature.images.data.UnsplashRepository
import com.satis.app.feature.images.data.UnsplashRepositoryImpl
import com.satis.app.feature.images.data.db.UnsplashQueries
import com.satis.app.feature.images.startup.UnsplashPreloadTask
import com.satis.app.feature.images.work.ImageWorker
import com.satis.app.startup.StartupTask
import com.satis.app.work.ChildWorkerFactory
import com.satis.app.work.WorkerKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ImagesModule {

  @Provides
  @Singleton
  fun provideUnsplashApi(retrofit: Retrofit): UnsplashApi = retrofit.create()

  @Provides
  @Singleton
  fun provideUnsplashQueries(database: Database): UnsplashQueries = database.unsplashQueries

}

@InstallIn(SingletonComponent::class)
@Module
abstract class ImagesBindingModule {

  @Binds
  abstract fun provideUnsplashRepository(bind: UnsplashRepositoryImpl): UnsplashRepository

  @Binds
  @IntoMap
  @WorkerKey(ImageWorker::class)
  abstract fun provideImageWorker(bind: ImageWorker.Factory): ChildWorkerFactory

  @Binds
  @IntoSet
  @Background
  abstract fun provideUnsplashTask(bind: UnsplashPreloadTask): StartupTask

}