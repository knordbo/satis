package com.satis.app.feature.colors.redux

import com.satis.app.feature.colors.persistence.ColorEntity
import com.satis.app.redux.ReduxDipatcher
import com.satis.app.redux.Store
import io.reactivex.Scheduler

class ColorDispatcher(
        private val colorMiddleware: ColorMiddleware,
        simpleStore: Store<ColorState>,
        scheduler: Scheduler)
    : ReduxDipatcher<ColorState, ColorViewState>(
        simpleStore,
        scheduler,
        ::colorStateToColorViewState) {

    init {
        simpleStore.dispatch(colorMiddleware.getColors())
    }

    fun deleteColor(colorEntity: ColorEntity) {
        store.dispatch(colorMiddleware.deleteColor(colorEntity))
    }

    fun addColor(color: String) {
        store.dispatch(colorMiddleware.addColor(color))
    }

    fun addColorReset() {
        store.dispatch(ColorActions.ColorAddReset)
    }

}