package com.satis.app.feature.account

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.satis.app.R
import org.junit.Test

class AccountFragmentTest {

    @Test
    fun showsAppInfo() {
        launchFragmentInContainer(themeResId = R.style.AppTheme) {
            createAccountFragment()
        }

        onView(withId(R.id.versionNumber)).check(matches(withText("Version: 1")))
    }

}