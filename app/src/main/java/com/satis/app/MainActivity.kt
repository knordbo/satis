package com.satis.app

import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.mvrx.BaseMvRxActivity
import com.satis.app.common.navigation.NavigationReselection
import com.satis.app.common.updater.ImmediateAppUpdater
import com.satis.app.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : BaseMvRxActivity() {

    private val navigationController: NavController by lazy { findNavController(R.id.navigationHostFragment) }

    private var appUpdateCalled = false

    @Inject lateinit var fragmentFactory: FragmentFactory
    @Inject lateinit var immediateAppUpdaterFactory: ImmediateAppUpdater.Factory
    @Inject lateinit var navigationReselection: NavigationReselection

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

        binding.bottomNav.setOnNavigationItemReselectedListener { menuItem ->
            navigationReselection.onNavigationItemReselected(menuItem.itemId)
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
