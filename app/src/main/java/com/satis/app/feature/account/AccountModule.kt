package com.satis.app.feature.account

import com.satis.app.IO
import org.koin.dsl.module.module

val accountModule = module {
    factory<AccountViewModel> { (initialState: AccountState) ->
        AccountViewModel(initialState, get(), get(IO))
    }
}