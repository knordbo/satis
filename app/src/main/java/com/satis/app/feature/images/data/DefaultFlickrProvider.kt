package com.satis.app.feature.images.data

import android.net.Uri
import com.satis.app.common.keyvalue.Key
import com.satis.app.common.keyvalue.KeyValueProvider
import com.satis.app.feature.images.PhotoState
import com.satis.app.feature.images.data.ImageSize.SIZE_100
import com.satis.app.feature.images.data.ImageSize.SIZE_640
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map

class DefaultFlickrProvider(
        private val flickrApi: FlickrApi,
        private val keyValueProvider: KeyValueProvider
) : FlickrProvider {
    private val popularImagesKey: Key<Flickr> = Key.of("flickr_popular_images")

    override suspend fun fetchPopularImages(): List<PhotoState> {
        val popularImages = flickrApi.getPopularImages().await()
        keyValueProvider.insert(popularImagesKey, popularImages)
        return popularImages.photos.photo.toState()
    }

    override fun streamPopularImages(): ReceiveChannel<List<PhotoState>> =
            keyValueProvider.getStream(popularImagesKey).map { flickr ->
                flickr.photos.photo.toState()
            }

    private fun List<Photo>.toState() = map {
        PhotoState(
                id = it.id,
                thumbnailUrl = it.toUri(SIZE_100),
                photoUrl = it.toUri(SIZE_640)
        )
    }

    private fun Photo.toUri(size: ImageSize): Uri =
            Uri.parse("https://farm$farm.staticflickr.com/$server/${id}_${secret}_${size.sizeStr}.jpg")
}

private enum class ImageSize(val sizeStr: String) {
    SIZE_75("s"),
    SIZE_100("t"),
    SIZE_150("q"),
    SIZE_240("m"),
    SIZE_320("n"),
    SIZE_640("z"),
    SIZE_1024("b")
}