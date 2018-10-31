package com.satis.app.feature.images.data

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface FlickrApi {

    @GET("https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&format=json&api_key=f0e6fbb5fdf1f3842294a1d21f84e8a6&per_page=500&nojsoncallback=1")
    fun getRecentImages(): Deferred<Flickr>

    @GET("https://api.flickr.com/services/rest/?method=flickr.photos.getPopular&user_id=66956608@N06&format=json&api_key=f0e6fbb5fdf1f3842294a1d21f84e8a6&per_page=500&nojsoncallback=1")
    fun getPopularImages(): Deferred<Flickr>
}