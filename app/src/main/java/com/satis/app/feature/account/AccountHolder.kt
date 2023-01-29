package com.satis.app.feature.account

import com.satis.app.common.prefs.AccountId
import com.satis.app.common.thread.assertMainThread
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class AccountHolder @Inject constructor() {
  @Volatile
  private var accountId: AccountId? = null

  fun setAccountId(value: String) {
    assertMainThread()
    if (accountId == null) {
      accountId = AccountId(value)
    }
  }

  fun requireAccountId(): AccountId = requireNotNull(accountId) { "Account must be set" }
}
