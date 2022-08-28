package com.satis.app

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.play.core.common.IntentSenderForResultStarter
import com.google.common.io.BaseEncoding.base64
import com.satis.app.common.theme.AppTheme
import com.satis.app.common.updater.ImmediateAppUpdater
import com.satis.app.feature.account.ui.AccountScreen
import com.satis.app.feature.cards.CardScreen
import com.satis.app.feature.images.ImageScreen
import com.satis.app.feature.images.ImagesScreen
import com.satis.app.feature.notifications.NotificationScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IntentSenderForResultStarter {

  @Inject lateinit var immediateAppUpdaterFactory: ImmediateAppUpdater.Factory

  private var appUpdateCalled = false

  private val immediateAppUpdater: ImmediateAppUpdater by lazy {
    immediateAppUpdaterFactory.create(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val navController = rememberNavController()
      AppTheme {
        Scaffold(
          bottomBar = {
            BottomNavigation {
              val navBackStackEntry by navController.currentBackStackEntryAsState()
              val currentDestination = navBackStackEntry?.destination
              bottomNavigationItems.forEach { screen ->
                BottomNavigationItem(
                  icon = { Icon(screen.icon, contentDescription = null) },
                  label = { Text(stringResource(screen.resourceId)) },
                  selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                  onClick = {
                    navController.navigate(screen.route) {
                      popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                      }
                      launchSingleTop = true
                      restoreState = true
                    }
                  }
                )
              }
            }
          }
        ) { innerPadding ->
          NavHost(navController,
            startDestination = Screen.Home.route,
            Modifier.padding(innerPadding)) {
            composable(Screen.Home.route) { CardScreen() }
            composable(Screen.Images.route) {
              ImagesScreen(navController = navController)
            }
            composable(Screen.Notifications.route) { NotificationScreen() }
            composable(Screen.Account.route) { AccountScreen() }
            composable("image?photoUrl={photoUrl}&description={description}", arguments = listOf(
              navArgument("photoUrl") { type = NavType.StringType },
              navArgument("description") {
                type = NavType.StringType
                defaultValue = ""
              },
            )) { backStackEntry ->
              ImageScreen(
                photoUrl = String(base64().decode(backStackEntry.arguments!!.getString("photoUrl")!!)!!),
                description = backStackEntry.arguments!!.getString("description"),
              )
            }
          }
        }
      }
    }
    appUpdateCalled = savedInstanceState?.getBoolean(APP_UPDATE_CALLED, false) ?: false
    checkNotificationPermission()
  }

  override fun onResume() {
    super.onResume()
    immediateAppUpdater.startAppUpdateIfNeeded(initialCall = !appUpdateCalled)
    appUpdateCalled = true
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putBoolean(APP_UPDATE_CALLED, appUpdateCalled)
  }

  @SuppressLint("UnsafeOptInUsageError")
  private fun checkNotificationPermission() {
    if (Build.VERSION.SDK_INT >= 33
      && applicationInfo.targetSdkVersion >= 33
      && ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.POST_NOTIFICATIONS
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      registerForActivityResult(
        ActivityResultContracts.RequestPermission()
      ) {}.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
  }

  sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Home : Screen("home", R.string.home, Icons.Filled.Home)
    object Images : Screen("images", R.string.images, Icons.Filled.Image)
    object Notifications :
      Screen("notifications", R.string.notifications, Icons.Filled.Notifications)

    object Account : Screen("account", R.string.account, Icons.Filled.Person)
  }

  companion object {
    fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
  }

}

private val bottomNavigationItems = listOf(
  MainActivity.Screen.Home,
  MainActivity.Screen.Images,
  MainActivity.Screen.Notifications,
  MainActivity.Screen.Account,
)

private const val APP_UPDATE_CALLED = "app_update_called"
