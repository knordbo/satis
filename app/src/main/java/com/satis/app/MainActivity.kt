package com.satis.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.RouterTransaction
import com.satis.app.feature.cards.ui.CardsController
import com.satis.app.feature.colors.ui.ColorController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val router = Conductor.attachRouter(this, controllerContainer, savedInstanceState)

        if (!router.hasRootController()) {
//            router.setRoot(RouterTransaction.with(ColorController()))
            router.setRoot(RouterTransaction.with(CardsController()))
        }
    }

}