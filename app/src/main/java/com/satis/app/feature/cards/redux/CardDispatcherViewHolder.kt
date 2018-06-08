package com.satis.app.feature.cards.redux

import android.app.Application
import com.satis.app.appComponent
import com.satis.app.redux.DefaultStore
import com.satis.app.redux.DispatcherHolder
import io.reactivex.android.schedulers.AndroidSchedulers

class CardDispatcherViewHolder(application: Application) : DispatcherHolder<CardState, CardActions, CardViewState, CardDispatcher>(
        dispatcher = CardDispatcher(
                cardMiddleware = CardMiddleware(application.appComponent().cardProvider()),
                colorStore = DefaultStore(INITIAL_COLOR_STATE, CardReducer),
                scheduler = AndroidSchedulers.mainThread()
        ),
        application = application
)