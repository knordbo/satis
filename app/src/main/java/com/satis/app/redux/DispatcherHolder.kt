package com.satis.app.redux

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel

abstract class DispatcherHolder<S : State, out VS : ViewState, out D : ReduxDipatcher<S, VS>>(val dispatcher: D, application: Application) : AndroidViewModel(application) {

    override fun onCleared() {
        dispatcher.onCleared()
        super.onCleared()
    }
}

