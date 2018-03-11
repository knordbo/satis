package com.satis.app.feature.colors.redux

import com.satis.app.feature.colors.persistence.ColorEntity
import com.satis.app.redux.ReduxDipatcher
import com.satis.app.redux.Store
import io.reactivex.Scheduler

class ColorDispatcher(
        private val colorMiddleware: ColorMiddleware,
        colorStore: Store<ColorState, ColorActions>,
        scheduler: Scheduler)
    : ReduxDipatcher<ColorState, ColorActions, ColorViewState>(
        colorStore,
        scheduler,
        ::colorStateToColorViewState) {

    init {
        store.dispatch(colorMiddleware.getColors())
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

    fun deleteColorReset() {
        store.dispatch(ColorActions.ColorDeleteReset)
    }

}