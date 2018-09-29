package com.satis.app.feature.colors.redux

import android.app.Application
import com.satis.app.redux.DefaultStore
import com.satis.app.redux.DispatcherHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.android.ext.android.get

class ColorDispatcherViewHolder(application: Application) : DispatcherHolder<ColorState, ColorActions, ColorViewState, ColorDispatcher>(
        dispatcher = ColorDispatcher(
                colorMiddleware = ColorMiddleware(application.get()),
                colorStore = DefaultStore(INITIAL_COLOR_STATE, ColorReducer),
                scheduler = AndroidSchedulers.mainThread()
        ),
        application = application
)