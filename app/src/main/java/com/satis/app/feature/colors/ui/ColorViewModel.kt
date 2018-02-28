package com.satis.app.feature.colors.ui

import android.arch.lifecycle.ViewModel
import com.satis.app.feature.colors.persistence.ColorDao
import com.satis.app.feature.colors.persistence.ColorEntity
import io.reactivex.Completable
import io.reactivex.Flowable

class ColorViewModel(private val colorDao: ColorDao) : ViewModel() {

    fun colors(): Flowable<List<ColorEntity>> = colorDao.getColors()

    fun addColor(color: String): Completable = Completable.fromAction {
        colorDao.insertColor(ColorEntity(color = color))
    }
}