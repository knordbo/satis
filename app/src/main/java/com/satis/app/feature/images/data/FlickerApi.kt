package com.satis.app.feature.images.data

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface FlickerApi {

    @GET("https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&format=json&api_key=f0e6fbb5fdf1f3842294a1d21f84e8a6&nojsoncallback=1")
    fun getRecentImages(): Deferred<Flicker>
}