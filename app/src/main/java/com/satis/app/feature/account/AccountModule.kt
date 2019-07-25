package com.satis.app.feature.account

import androidx.fragment.app.Fragment
import com.satis.app.common.fragment.FragmentKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AccountModule {

    @Binds
    @IntoMap
    @FragmentKey(AccountFragment::class)
    abstract fun provideAccountFragment(bind: AccountFragment): Fragment

}