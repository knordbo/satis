package com.satis.app

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.mvrx.BaseMvRxActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvRxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val navigationController = findNavController(R.id.navigationHostFragment)

        bottomNav.setupWithNavController(navigationController)
        setupActionBarWithNavController(navigationController)

        navigationController.addOnNavigatedListener { _, _ ->
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

}