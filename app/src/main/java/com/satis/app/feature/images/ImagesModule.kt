package com.satis.app.feature.images

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.satis.app.IO
import com.satis.app.feature.images.data.DefaultFlickrProvider
import com.satis.app.feature.images.data.FlickrApi
import com.satis.app.feature.images.data.FlickrProvider
import com.satis.app.feature.images.work.ImageWorker
import com.satis.app.utils.retrofit.create
import org.koin.dsl.module.module
import retrofit2.Retrofit

val imagesModule = module {

    single<FlickrApi> {
        get<Retrofit>().create()
    }

    single<FlickrProvider> {
        DefaultFlickrProvider(get(), get())
    }

    factory<ImagesViewModel> { (initialState: ImagesState) ->
        ImagesViewModel(initialState, get(), get(IO), get(IO))
    }

    factory<ListenableWorker>(ImageWorker::class.java.name) { (context: Context, workerParameters: WorkerParameters) ->
        ImageWorker(context, workerParameters, get(IO), get(), get())
    }
}