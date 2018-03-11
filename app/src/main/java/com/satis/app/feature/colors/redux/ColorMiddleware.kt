package com.satis.app.feature.colors.redux

import com.satis.app.feature.colors.persistence.ColorDao
import com.satis.app.feature.colors.persistence.ColorEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class ColorMiddleware(private val colorDao: ColorDao) {
    fun getColors(): Flowable<ColorActions> = colorDao.getColors()
            .map { ColorActions.ColorResultSuccess(it) }
            .cast(ColorActions::class.java)
            .subscribeOn(Schedulers.io())
            .onErrorReturn { ColorActions.ColorResultError(it) }
            .startWith(ColorActions.ColorProgress)

    fun addColor(color: String): Flowable<ColorActions> = addColorCompletable(color)
            .andThen(Flowable.just(ColorActions.ColorAddSuccess(color)))
            .cast(ColorActions::class.java)
            .subscribeOn(Schedulers.io())

    fun deleteColor(colorEntity: ColorEntity): Flowable<ColorActions> = deleteColorCompletable(colorEntity)
            .andThen(Flowable.just(ColorActions.ColorDeleteSuccess(colorEntity)))
            .cast(ColorActions::class.java)
            .subscribeOn(Schedulers.io())

    private fun addColorCompletable(color: String) = Completable.fromCallable {
        colorDao.insertColor(ColorEntity(color = color))
    }

    private fun deleteColorCompletable(colorEntity: ColorEntity) = Completable.fromCallable {
        colorDao.deleteColor(colorEntity)
    }
}