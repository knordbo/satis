package com.satis.app.feature.account

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.satis.app.feature.account.ui.AccountScreen
import org.junit.Rule
import org.junit.Test

class AccountFragmentTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun showsAppInfo() {
    composeTestRule.setContent {
      AccountScreen(createAccountViewModel())
    }
    composeTestRule.onNodeWithText("Version: 1").assertIsDisplayed()
  }

  @Test
  fun showsNotificationToken() {
    composeTestRule.setContent {
      AccountScreen(createAccountViewModel())
    }
    composeTestRule.onNodeWithText("Notification token: my_token").assertIsDisplayed()
  }

}