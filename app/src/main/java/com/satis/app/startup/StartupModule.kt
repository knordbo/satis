package com.satis.app.startup

import com.satis.app.common.annotations.Background
import com.satis.app.common.annotations.Main
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds

@InstallIn(SingletonComponent::class)
@Module
abstract class StartupModule {

  @Binds
  @Main
  abstract fun provideMainThreadStartupTasks(bind: MainThreadStartupTasks): StartupTasks

  @Binds
  @IntoSet
  @Main
  abstract fun provideThemeLoaderTask(bind: ThemeLoaderTask): StartupTask

  @Multibinds
  @Main
  abstract fun provideMainStartupTasksSet(): Set<StartupTask>

  @Binds
  @Background
  abstract fun provideBackgroundThreadStartupTasks(bind: BackgroundThreadStartupTasks): StartupTasks

  @Multibinds
  @Background
  abstract fun provideBackgroundStartupTasksSet(): Set<StartupTask>

  @Binds
  @IntoSet
  @Background
  abstract fun provideWorkSchedulerTask(bind: WorkSchedulerTask): StartupTask
}