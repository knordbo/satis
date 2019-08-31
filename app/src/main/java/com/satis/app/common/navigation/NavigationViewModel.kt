package com.satis.app.common.navigation

import com.satis.app.utils.coroutines.BaseViewModel

class NavigationViewModel(
        initialState: NavigationState
) : BaseViewModel<NavigationState>(
        initialState = initialState
) {
    fun tabSelected(tab: Tab) {
        setState {
            copy(currentTab = tab)
        }
    }

    fun tabReselected(tab: Tab) {
        setState {
            copy(reselectedTab = tab)
        }
    }

    fun tabReselectedHandled() {
        setState {
            copy(reselectedTab = null)
        }
    }
}