package com.satis.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.satis.app.common.navigation.NavigationReselectionUpdater
import com.satis.app.common.updater.ImmediateAppUpdater
import com.satis.app.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val navigationController: NavController by lazy { findNavController(R.id.navigationHostFragment) }

    private var appUpdateCalled = false

    @Inject lateinit var fragmentFactory: FragmentFactory
    @Inject lateinit var immediateAppUpdaterFactory: ImmediateAppUpdater.Factory
    @Inject lateinit var navigationReselectionUpdater: NavigationReselectionUpdater

    private val immediateAppUpdater: ImmediateAppUpdater by lazy {
        immediateAppUpdaterFactory.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
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

}

private const val APP_UPDATE_CALLED = "app_update_called"
