package com.satis.app.feature.images

import com.satis.app.IO
import com.satis.app.feature.images.data.DefaultFlickerProvider
import com.satis.app.feature.images.data.FlickerApi
import com.satis.app.feature.images.data.FlickerProvider
import com.satis.app.utils.retrofit.create
import org.koin.dsl.module.module
import retrofit2.Retrofit

val imagesModule = module {

    single<FlickerApi> {
        get<Retrofit>().create()
    }

    single<FlickerProvider> {
        DefaultFlickerProvider(get())
    }

    factory<ImagesViewModel> { (initialState: ImagesState) ->
        ImagesViewModel(initialState, get(), get(IO))
    }
}