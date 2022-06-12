package com.satis.app.feature.account.appinfo

import android.content.Context
import com.satis.app.BuildConfig
import com.satis.app.common.annotations.Background
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class AppInfoRetrieverImpl @Inject constructor(
  @ApplicationContext private val context: Context,
  @Background private val background: CoroutineContext
) : AppInfoRetriever {
  override suspend fun getAppInfo(): AppInfo {
    return withContext(background) {
      AppInfo(
        versionCode = context.packageManager.getPackageInfo(context.packageName, 0).longVersionCode,
        buildTime = BuildConfig.BUILD_TIME
      )
    }
  }
}