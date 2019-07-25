package com.satis.app.common.fragment

import androidx.fragment.app.Fragment
import dagger.MapKey
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY_GETTER
import kotlin.annotation.AnnotationTarget.PROPERTY_SETTER
import kotlin.reflect.KClass

@Target(FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER)
@Retention(value = RUNTIME)
@MapKey
annotation class FragmentKey(val value: KClass<out Fragment>)