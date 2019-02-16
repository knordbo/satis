package com.satis.app.utils.system

fun <U> forNameAsSubclass(className: String): Class<out U> = Class.forName(className).asSubclass()

fun <U> Class<*>.asSubclass(): Class<out U> = this as Class<out U>