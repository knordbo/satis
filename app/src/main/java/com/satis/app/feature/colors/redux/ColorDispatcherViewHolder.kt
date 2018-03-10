package com.satis.app.feature.colors.redux

import android.app.Application
import com.satis.app.appComponent
import com.satis.app.redux.DispatcherHolder
import com.satis.app.redux.SimpleStore
import io.reactivex.android.schedulers.AndroidSchedulers

class ColorDispatcherViewHolder(application: Application) : DispatcherHolder<ColorState, ColorViewState, ColorDispatcher>(
        dispatcher = ColorDispatcher(
                colorMiddleware = ColorMiddleware(application.appComponent().colorDao()),
                simpleStore = SimpleStore(INITIAL_COLOR_STATE, ColorReducer),
                scheduler = AndroidSchedulers.mainThread()
        ),
        application = application
)