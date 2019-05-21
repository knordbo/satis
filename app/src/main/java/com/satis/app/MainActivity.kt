package com.satis.app

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.mvrx.BaseMvRxActivity
import com.airbnb.mvrx.viewModel
import com.satis.app.Tab.ACCOUNT
import com.satis.app.Tab.HOME
import com.satis.app.Tab.IMAGES
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : BaseMvRxActivity() {

    private val navigationController: NavController by lazy { findNavController(R.id.navigationHostFragment) }
    private val navigationViewModel: NavigationViewModel by viewModel()
    private val immediateAppUpdater: ImmediateAppUpdater by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = get()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        bottomNav.setupWithNavController(navigationController)
        setupActionBarWithNavController(
                navController = navigationController,
                configuration = AppBarConfiguration(setOf(R.id.home, R.id.images, R.id.account))
        )

        navigationController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            navigationViewModel.tabSelected(destination.asTab())
        }
        bottomNav.setOnNavigationItemReselectedListener {
            navigationViewModel.tabReselected(navigationController.currentDestination!!.asTab())
        }
    }

    override fun onResume() {
        super.onResume()
        immediateAppUpdater.startAppUpdateIfNeeded()
    }

    override fun onSupportNavigateUp() = navigationController.navigateUp()

}

private fun NavDestination.asTab() = when (id) {
    R.id.home -> HOME
    R.id.images -> IMAGES
    R.id.account -> ACCOUNT
    else -> throw RuntimeException("Unsupported tab")
}