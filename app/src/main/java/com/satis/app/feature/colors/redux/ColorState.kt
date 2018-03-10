package com.satis.app.feature.colors.redux

import com.satis.app.feature.colors.persistence.ColorEntity
import com.satis.app.redux.State

data class ColorState(
        val isLoading: Boolean = false,
        val colors: List<ColorEntity> = emptyList(),
        val successfullyAddedColor: String? = null,
        val successfullyDeletedColor: ColorEntity? = null,
        val error: Throwable? = null
) : State

val INITIAL_COLOR_STATE = ColorState()