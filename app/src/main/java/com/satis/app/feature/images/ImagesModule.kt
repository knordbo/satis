package com.satis.app.feature.images

import android.content.Context
import androidx.work.WorkerParameters
import com.satis.app.Io
import com.satis.app.feature.images.data.DefaultUnsplashProvider
import com.satis.app.feature.images.data.UnsplashApi
import com.satis.app.feature.images.data.UnsplashProvider
import com.satis.app.feature.images.work.ImageWorker
import com.satis.app.utils.retrofit.create
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val imagesModule = module {

    single<UnsplashApi> {
        get<Retrofit>().create()
    }

    single<UnsplashProvider> {
        DefaultUnsplashProvider(get(), get())
    }

    factory<ImagesViewModel> { (initialState: ImagesState) ->
        ImagesViewModel(initialState, get(), get(named<Io>()))
    }

    factory<ImagesFragment> {
        ImagesFragment()
    }

    factory<ImageFragment> {
        ImageFragment()
    }

    factory<ImageWorker> { (context: Context, workerParameters: WorkerParameters) ->
        ImageWorker(context, workerParameters, get(named<Io>()), get(), get())
    }
}