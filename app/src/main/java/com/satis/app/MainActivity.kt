package com.satis.app

import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.mvrx.BaseMvRxActivity
import com.airbnb.mvrx.viewModel
import com.satis.app.common.navigation.NavigationViewModel
import com.satis.app.common.navigation.Tab
import com.satis.app.common.navigation.Tab.ACCOUNT
import com.satis.app.common.navigation.Tab.HOME
import com.satis.app.common.navigation.Tab.IMAGES
import com.satis.app.common.updater.ImmediateAppUpdater
import com.satis.app.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : BaseMvRxActivity() {

    private val navigationController: NavController by lazy { findNavController(R.id.navigationHostFragment) }
    private val navigationViewModel: NavigationViewModel by viewModel()

    private var appUpdateCalled = false

    @Inject lateinit var fragmentFactory: FragmentFactory
    @Inject lateinit var immediateAppUpdaterFactory: ImmediateAppUpdater.Factory

    private val immediateAppUpdater: ImmediateAppUpdater by lazy {
        immediateAppUpdaterFactory.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        supportFragmentManager.fragmentFactory = fragmentFactory

        super.onCreate(savedInstanceState)
        appUpdateCalled = savedInstanceState?.getBoolean(APP_UPDATE_CALLED, false) ?: false

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.bottomNav.setupWithNavController(navigationController)
        setupActionBarWithNavController(
                navController = navigationController,
                configuration = AppBarConfiguration(setOf(R.id.home, R.id.images, R.id.account))
        )

        navigationController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            destination.runIfTab { tab ->
                navigationViewModel.tabSelected(tab)
            }
        }
        binding.bottomNav.setOnNavigationItemReselectedListener {
            navigationController.currentDestination?.runIfTab { tab ->
                navigationViewModel.tabReselected(tab)
            }
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

}

private const val APP_UPDATE_CALLED = "app_update_called"

private fun NavDestination.runIfTab(block: (Tab) -> Unit) {
    val tab = when (id) {
        R.id.home -> HOME
        R.id.images -> IMAGES
        R.id.account -> ACCOUNT
        else -> null
    }
    if (tab != null) {
        block(tab)
    }
}