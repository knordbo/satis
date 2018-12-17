package com.satis.app.feature.playground

import io.reactivex.Single
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface PlaygroundProvider {
    fun getItems(query: String): Single<List<String>>
}

class DefaultPlaygroundProvider: PlaygroundProvider {

    private val simpleDateFormat: SimpleDateFormat by lazy { SimpleDateFormat("dd.MM.YY HH:mm", Locale.US) }

    override fun getItems(query: String): Single<List<String>> = Single.fromCallable {
        Thread.sleep(2000)

        val time = simpleDateFormat.format(Date())
        (0..100).map {
            "$query: $time"
        }
    }
}