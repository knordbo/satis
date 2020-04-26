package com.satis.app.feature.images

import androidx.fragment.app.Fragment
import com.satis.app.Database
import com.satis.app.common.annotations.Background
import com.satis.app.common.fragment.FragmentKey
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
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module(includes = [ImagesBindingModule::class])
object ImagesModule {

  @Provides
  @Singleton
  fun provideUnsplashApi(retrofit: Retrofit): UnsplashApi = retrofit.create()

  @Provides
  @Singleton
  fun provideUnsplashQueries(database: Database): UnsplashQueries = database.unsplashQueries

}

@Module
abstract class ImagesBindingModule {

  @Binds
  @IntoMap
  @FragmentKey(ImagesFragment::class)
  abstract fun provideImagesFragment(bind: ImagesFragment): Fragment

  @Binds
  @IntoMap
  @FragmentKey(ImageFragment::class)
  abstract fun provideImageFragment(bind: ImageFragment): Fragment

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