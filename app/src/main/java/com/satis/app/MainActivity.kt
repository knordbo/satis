package com.satis.app

import android.os.Bundle
import com.airbnb.mvrx.BaseMvRxActivity
import com.satis.app.feature.cards.CardFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvRxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, CardFragment())
                .commit()
    }

}