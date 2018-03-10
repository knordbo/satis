package com.satis.app.feature.colors.redux

fun colorStateToColorViewState(colorState: ColorState) = with(colorState) {
    ColorViewState(
            isLoading = isLoading,
            error = error,
            colors = colors,
            successfullyAddedColor = successfullyAddedColor,
            successfullyDeletedColor = successfullyDeletedColor?.color
    )
}