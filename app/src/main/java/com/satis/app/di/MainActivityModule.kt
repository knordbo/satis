package com.satis.app.di

import com.satis.app.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

}