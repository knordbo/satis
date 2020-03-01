package com.satis.app.feature.playground

import androidx.fragment.app.Fragment
import com.satis.app.common.fragment.FragmentKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PlaygroundModule {

  @Binds
  @IntoMap
  @FragmentKey(PlaygroundFragment::class)
  abstract fun providePlaygroundFragment(bind: PlaygroundFragment): Fragment

}