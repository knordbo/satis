package com.satis.app

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.mvrx.BaseMvRxActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvRxActivity() {

    private val navigationController: NavController
        get() = findNavController(R.id.navigationHostFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        bottomNav.setupWithNavController(navigationController)
        setupActionBarWithNavController(navigationController)

        navigationController.addOnNavigatedListener { _, _ ->
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

}