package com.satis.app.di.account

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.satis.app.AccountDatabase
import com.satis.app.common.account.AccountId
import com.satis.app.common.annotations.AccountDatabaseName
import com.satis.app.common.annotations.AccountPath
import com.satis.app.feature.cards.CardAccountModule
import com.satis.app.feature.images.ImagesAccountModule
import com.satis.app.feature.notifications.NotificationAccountModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.migration.DisableInstallInCheck

@Module(includes = [
  CardAccountModule::class,
  ImagesAccountModule::class,
  NotificationAccountModule::class,
])
@DisableInstallInCheck
object SingletonAccountModule {

  @Provides
  @AccountScope
  @AccountPath
  fun provideAccountFolderPath(accountId: AccountId): String {
    return "accounts/${accountId.value}"
  }

  @Provides
  @AccountScope
  @AccountDatabaseName
  fun provideAccountDatabaseName(@AccountPath accountPath: String): String {
    return "${accountPath}/satis.sqldelight.accountdb"
  }

  @Provides
  @AccountScope
  fun provideAccountDatabase(
    @ApplicationContext context: Context,
    @AccountDatabaseName accountDatabaseName: String,
  ): AccountDatabase =
    AccountDatabase(
      driver = AndroidSqliteDriver(
        schema = AccountDatabase.Schema,
        context = context,
        name = accountDatabaseName,
        useNoBackupDirectory = true
      ),
    )

}
