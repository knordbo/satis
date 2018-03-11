package com.satis.app.feature.colors.redux

import com.satis.app.feature.colors.persistence.ColorEntity

sealed class ColorActions {
    data class ColorResultSuccess(val colors: List<ColorEntity>) : ColorActions()
    data class ColorResultError(val error: Throwable) : ColorActions()
    data class ColorAddSuccess(val color: String) : ColorActions()
    data class ColorDeleteSuccess(val color: ColorEntity) : ColorActions()
    object ColorProgress : ColorActions()
    object ColorAddReset : ColorActions()
    object ColorDeleteReset : ColorActions()
}