package com.satis.app.di

import com.satis.app.flipper.FlipperModule
import dagger.Module

@Module(includes = [FlipperModule::class])
object VariantModule