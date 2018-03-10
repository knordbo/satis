package com.satis.app.feature.colors.redux

import com.satis.app.redux.Action
import com.satis.app.redux.Reducer

object ColorReducer : Reducer<ColorState> {
    override fun reduce(oldState: ColorState, action: Action): ColorState {
        return when (action) {
            is ColorActions.ColorProgress -> oldState.copy(isLoading = true)
            is ColorActions.ColorResultSuccess -> oldState.copy(isLoading = false, colors = action.colors)
            is ColorActions.ColorResultError -> oldState.copy(isLoading = false, error = action.error)
            is ColorActions.ColorAddSuccess -> oldState.copy(successfullyAddedColor = action.color)
            is ColorActions.ColorDeleteSuccess -> oldState.copy(successfullyDeletedColor = action.color)
            is ColorActions.ColorAddReset -> oldState.copy(successfullyAddedColor = null)
            else -> oldState
        }
    }
}