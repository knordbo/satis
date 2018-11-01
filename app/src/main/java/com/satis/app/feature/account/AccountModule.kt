package com.satis.app.feature.account

import org.koin.dsl.module.module

val accountModule = module {
    factory<AccountViewModel> { (initialState: AccountState) ->
        AccountViewModel(initialState, get())
    }
}