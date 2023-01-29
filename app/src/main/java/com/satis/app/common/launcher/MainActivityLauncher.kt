package com.satis.app.common.launcher

import android.app.Activity
import android.content.Intent
import com.satis.app.MainActivity
import javax.inject.Inject

class MainActivityLauncher @Inject constructor(
  private val activity: Activity,
) {
  fun launchAdjacent(accountId: String) {
    val intent = Intent(activity, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT or Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.putExtra(ACCOUNT_ID, accountId)
    activity.startActivity(intent)
  }
}

const val ACCOUNT_ID = "account_id"
