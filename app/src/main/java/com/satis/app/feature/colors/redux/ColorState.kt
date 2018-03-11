package com.satis.app.feature.colors.redux

import com.satis.app.feature.colors.persistence.ColorEntity

data class ColorState(
        val isLoading: Boolean = false,
        val colors: List<ColorEntity> = emptyList(),
        val successfullyAddedColor: String? = null,
        val successfullyDeletedColor: ColorEntity? = null,
        val error: Throwable? = null
)

val INITIAL_COLOR_STATE = ColorState()