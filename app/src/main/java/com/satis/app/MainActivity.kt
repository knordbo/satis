package com.satis.app

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.mvrx.BaseMvRxActivity
import com.airbnb.mvrx.viewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemReselectedListener
import com.satis.app.Tab.ACCOUNT
import com.satis.app.Tab.HOME
import com.satis.app.Tab.IMAGES
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.RuntimeException

class MainActivity : BaseMvRxActivity(), BottomNavigationView.OnNavigationItemSelectedListener, OnNavigationItemReselectedListener {

    private val navigationController by lazy { findNavController(R.id.navigationHostFragment) }
    private val navigationViewModel: NavigationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        bottomNav.setupWithNavController(navigationController)
        setupActionBarWithNavController(
                navController = navigationController,
                configuration = AppBarConfiguration(setOf(R.id.home, R.id.images, R.id.account))
        )

        bottomNav.setOnNavigationItemSelectedListener(this)
        bottomNav.setOnNavigationItemReselectedListener(this)
    }

    override fun onSupportNavigateUp() = navigationController.navigateUp()

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        // Overriding default behaviour of NavigationUI which adds a way too long fade animation
        navigationController.navigate(menuItem.itemId)
        navigationViewModel.tabSelected(menuItem.asTab())
        return true
    }

    override fun onNavigationItemReselected(menuItem: MenuItem) {
        navigationViewModel.tabReselected(menuItem.asTab())
    }

}

private fun MenuItem.asTab() = when (itemId) {
    R.id.home -> HOME
    R.id.images -> IMAGES
    R.id.account -> ACCOUNT
    else -> throw RuntimeException("Unsupported tab")
}