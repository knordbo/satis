package com.satis.app.feature.playground

import androidx.fragment.app.Fragment
import com.satis.app.common.fragment.FragmentKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@InstallIn(SingletonComponent::class)
@Module
abstract class PlaygroundModule {

  @Binds
  @IntoMap
  @FragmentKey(PlaygroundFragment::class)
  abstract fun providePlaygroundFragment(bind: PlaygroundFragment): Fragment

}