package com.satis.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
import javax.inject.Inject

class MainActivity @Inject constructor(
  private val fragmentFactory: FragmentFactory,
  private val immediateAppUpdaterFactory: ImmediateAppUpdater.Factory,
  private val navigationReselectionUpdater: NavigationReselectionUpdater,
) : AppCompatActivity(), IntentSenderForResultStarter {

  private val navigationController: NavController by lazy { findNavController(R.id.navigationHostFragment) }

  private var appUpdateCalled = false

  private val immediateAppUpdater: ImmediateAppUpdater by lazy {
    immediateAppUpdaterFactory.create(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    supportFragmentManager.fragmentFactory = fragmentFactory

    super.onCreate(savedInstanceState)
    appUpdateCalled = savedInstanceState?.getBoolean(APP_UPDATE_CALLED, false) ?: false

    val binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setSupportActionBar(binding.toolbar)

    binding.bottomNav.setupWithNavController(navigationController)
    setupActionBarWithNavController(
      navController = navigationController,
      configuration = AppBarConfiguration(setOf(R.id.home, R.id.images, R.id.notification, R.id.account))
    )

    binding.bottomNav.setOnItemReselectedListener { menuItem ->
      navigationReselectionUpdater.onNavigationItemReselected(menuItem.itemId)
    }
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

  companion object {
    fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
  }

}

private const val APP_UPDATE_CALLED = "app_update_called"
