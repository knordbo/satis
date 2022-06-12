package com.satis.app

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.play.core.common.IntentSenderForResultStarter
import com.satis.app.common.navigation.NavigationReselectionUpdater
import com.satis.app.common.updater.ImmediateAppUpdater
import com.satis.app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IntentSenderForResultStarter {

  @Inject lateinit var fragmentFactory: FragmentFactory
  @Inject lateinit var immediateAppUpdaterFactory: ImmediateAppUpdater.Factory
  @Inject lateinit var navigationReselectionUpdater: NavigationReselectionUpdater

  private val navigationController: NavController by lazy { findNavController(R.id.navigationHostFragment) }

  private var appUpdateCalled = false

  private val immediateAppUpdater: ImmediateAppUpdater by lazy {
    immediateAppUpdaterFactory.create(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    supportFragmentManager.fragmentFactory = fragmentFactory

    appUpdateCalled = savedInstanceState?.getBoolean(APP_UPDATE_CALLED, false) ?: false

    val binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setSupportActionBar(binding.toolbar)

    binding.bottomNav.setupWithNavController(navigationController)
    setupActionBarWithNavController(
      navController = navigationController,
      configuration = AppBarConfiguration(
        setOf(
          R.id.home,
          R.id.images,
          R.id.notification,
          R.id.account,
        )
      )
    )

    binding.bottomNav.setOnItemReselectedListener { menuItem ->
      navigationReselectionUpdater.onNavigationItemReselected(menuItem.itemId)
    }
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

  override fun onSupportNavigateUp() = navigationController.navigateUp()

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

  companion object {
    fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
  }

}

private const val APP_UPDATE_CALLED = "app_update_called"
