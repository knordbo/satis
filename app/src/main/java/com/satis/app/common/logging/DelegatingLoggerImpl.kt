package com.satis.app.common.logging

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DelegatingLoggerImpl @Inject constructor(
    private val loggers: Set<@JvmSuppressWildcards Logger>
) : Logger {
  override fun log(tag: String, message: String) {
    loggers.forEach { logger ->
      logger.log(tag, message)
    }
  }
}