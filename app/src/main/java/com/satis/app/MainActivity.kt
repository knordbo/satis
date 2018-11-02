package com.satis.app

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.mvrx.BaseMvRxActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemReselectedListener
import com.satis.app.common.fragment.ReselectableFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvRxActivity(), BottomNavigationView.OnNavigationItemSelectedListener, OnNavigationItemReselectedListener {

    private val navigationController by lazy { findNavController(R.id.navigationHostFragment) }

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

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        // Overriding default behaviour of NavigationUI which adds a way too long fade animation
        navigationController.navigate(menuItem.itemId)
        return true
    }

    override fun onNavigationItemReselected(menuItem: MenuItem) {
        // Invoke the current fragment if it is reselectable
        (currentFragment() as? ReselectableFragment)?.onFragmentReselected()
    }

    private fun currentFragment(): Fragment? = supportFragmentManager.findFragmentById(R.id.navigationHostFragment)
            ?.childFragmentManager
            ?.fragments
            ?.firstOrNull {
                it.javaClass == (navigationController.currentDestination as? FragmentNavigator.Destination)?.fragmentClass
            }

}