package com.satis.app.common.service

import android.app.Service
import dagger.MapKey
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(value = AnnotationRetention.RUNTIME)
@MapKey
annotation class ServiceKey(val value: KClass<out Service>)
