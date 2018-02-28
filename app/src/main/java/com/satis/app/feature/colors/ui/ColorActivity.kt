package com.satis.app.feature.colors.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.satis.app.R
import com.satis.app.appComponent
import com.satis.app.utils.rx.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_color.*

class ColorActivity : AppCompatActivity() {

    private lateinit var colorViewModel: ColorViewModel
    private val disposable = CompositeDisposable()
    private val colorAdapter = ColorAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color)

        rv.adapter = colorAdapter

        val colorViewModelFactory = appComponent().colorViewModelFactory()
        colorViewModel = ViewModelProviders.of(this, colorViewModelFactory).get(ColorViewModel::class.java)
        addColor.setOnClickListener { addColor() }
    }

    override fun onStart() {
        super.onStart()
        streamColors()
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    private fun streamColors() {
        disposable += colorViewModel.colors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    colorAdapter.bind(it)
                }, {
                    Toast.makeText(this, "Error $it", Toast.LENGTH_SHORT).show()
                })
    }

    private fun addColor() {
        disposable += colorViewModel.addColor(color.text.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    color.setText("")
                }, {
                    Toast.makeText(this, "Error $it", Toast.LENGTH_SHORT).show()
                })
    }
}