package com.satis.app.feature.cards.data

data class DbCard(
        var title: String = "",
        var message: String = "",
        var likes: Map<String, Boolean> = emptyMap(),
        var dislikes: Map<String, Boolean> = emptyMap()
)