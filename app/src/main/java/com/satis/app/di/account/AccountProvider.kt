package com.satis.app.di.account

import com.satis.app.common.account.AccountId

interface AccountProvider<T> {
  fun get(accountId: AccountId): T
}
