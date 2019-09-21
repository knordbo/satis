package com.satis.app.startup

import com.satis.app.common.annotations.Background
import com.satis.app.common.annotations.Main
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module(includes = [StartupBindingModule::class])
class StartupModule {

}

@Module
abstract class StartupBindingModule {

    @Binds
    @Main
    abstract fun provideMainThreadStartupTasks(bind: MainThreadStartupTasks): StartupTasks

    @Binds
    @IntoSet
    @Main
    abstract fun provideBuildVariantConfigurationTask(bind: BuildVariantConfigurationTask): StartupTask

    @Binds
    @Background
    abstract fun provideBackgroundThreadStartupTasks(bind: BackgroundThreadStartupTasks): StartupTasks

    @Binds
    @IntoSet
    @Background
    abstract fun provideThemeLoaderTask(bind: ThemeLoaderTask): StartupTask

    @Binds
    @IntoSet
    @Background
    abstract fun provideWorkSchedulerTask(bind: WorkSchedulerTask): StartupTask
}