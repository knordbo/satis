package com.satis.app.feature.images

import androidx.fragment.app.Fragment
import com.satis.app.common.fragment.FragmentKey
import com.satis.app.feature.images.data.UnsplashApi
import com.satis.app.feature.images.data.UnsplashRepository
import com.satis.app.feature.images.data.UnsplashRepositoryImpl
import com.satis.app.feature.images.work.ImageWorker
import com.satis.app.utils.network.create
import com.satis.app.work.ChildWorkerFactory
import com.satis.app.work.WorkerKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [ImagesBindingModule::class])
class ImagesModule {

    @Provides
    @Singleton
    fun provideUnsplashApi(retrofit: Retrofit): UnsplashApi = retrofit.create()

}

@Module
abstract class ImagesBindingModule {

    @Binds
    @IntoMap
    @FragmentKey(ImagesFragment::class)
    abstract fun provideImagesFragment(bind: ImagesFragment): Fragment

    @Binds
    abstract fun provideUnsplashRepository(bind: UnsplashRepositoryImpl): UnsplashRepository

    @Binds
    @IntoMap
    @WorkerKey(ImageWorker::class)
    abstract fun provideImageWorker(bind: ImageWorker.Factory): ChildWorkerFactory


}