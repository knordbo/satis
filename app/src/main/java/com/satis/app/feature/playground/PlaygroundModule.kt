package com.satis.app.feature.playground

import com.satis.app.IO
import org.koin.dsl.module.module

val playgroundModule = module {

    single<PlaygroundProvider> { DefaultPlaygroundProvider() }

    factory<PlaygroundViewModel> { (initialState: PlaygroundState) ->
        PlaygroundViewModel(initialState, get(), get(IO))
    }

    factory<PlaygroundFragment> {
        PlaygroundFragment()
    }

}