package com.satis.app.feature.colors.redux

import android.app.Application
import com.satis.app.appComponent
import com.satis.app.redux.DefaultStore
import com.satis.app.redux.DispatcherHolder
import io.reactivex.android.schedulers.AndroidSchedulers

class ColorDispatcherViewHolder(application: Application) : DispatcherHolder<ColorState, ColorActions, ColorViewState, ColorDispatcher>(
        dispatcher = ColorDispatcher(
                colorMiddleware = ColorMiddleware(application.appComponent().colorDao()),
                colorStore = DefaultStore(INITIAL_COLOR_STATE, ColorReducer),
                scheduler = AndroidSchedulers.mainThread()
        ),
        application = application
)