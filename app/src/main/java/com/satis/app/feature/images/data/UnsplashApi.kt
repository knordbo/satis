package com.satis.app.feature.images.data

import com.satis.app.BuildConfig.UNSPLASH_CLIENT_ID
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    @Headers(
            "Accept-Version: v1",
            "Authorization: Client-ID $UNSPLASH_CLIENT_ID"
    )
    @GET("https://api.unsplash.com/search/photos")
    suspend fun searchPhotos(
            @Query("per_page") perPage: Int = 30,
            @Query("query") query: String
    ): Unsplash
}