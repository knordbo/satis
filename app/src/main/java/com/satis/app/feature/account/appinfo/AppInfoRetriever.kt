package com.satis.app.feature.account.appinfo

interface AppInfoRetriever {
    suspend fun getAppInfo(): AppInfo
}