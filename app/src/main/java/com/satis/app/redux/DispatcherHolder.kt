package com.satis.app.redux

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

abstract class DispatcherHolder<State, in Action, out ViewState, out Dispatcher : ReduxDipatcher<State, Action, ViewState>>(
        val dispatcher: Dispatcher,
        application: Application
) : AndroidViewModel(application) {

    override fun onCleared() {
        dispatcher.onCleared()
        super.onCleared()
    }
}

