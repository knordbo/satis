package com.satis.app.feature.playground

import com.satis.app.Io
import org.koin.core.qualifier.named
import org.koin.dsl.module

val playgroundModule = module {

    factory<PlaygroundViewModel> { (initialState: PlaygroundState) ->
        PlaygroundViewModel(initialState, get(), get(named<Io>()))
    }

    factory<PlaygroundFragment> {
        PlaygroundFragment()
    }

}