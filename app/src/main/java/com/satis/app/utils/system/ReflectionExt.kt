package com.satis.app.utils.system

fun <U> forNameAsSubclass(className: String): Class<out U>? = try {
  Class.forName(className).asSubclass()
} catch (exception: ClassNotFoundException) {
  null
}

private fun <U> Class<*>.asSubclass(): Class<out U> = this as Class<out U>