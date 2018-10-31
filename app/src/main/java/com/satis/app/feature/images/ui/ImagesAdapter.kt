package com.satis.app.feature.images.ui

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.satis.app.R
import com.satis.app.feature.images.PhotoState
import com.satis.app.feature.images.ui.ImagesAdapter.ImageViewHolder
import com.satis.app.utils.view.layoutInflater

class ImagesAdapter(private val requestManager: RequestManager) : ListAdapter<PhotoState, ImageViewHolder>(object : DiffUtil.ItemCallback<PhotoState>() {
    override fun areItemsTheSame(oldItem: PhotoState, newItem: PhotoState): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: PhotoState, newItem: PhotoState): Boolean = oldItem == newItem
}), ListPreloader.PreloadModelProvider<PhotoState> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
            ImageViewHolder(parent.layoutInflater.inflate(R.layout.image_item, parent, false) as AppCompatImageView)

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position)
        Glide.with(holder.imageView)
                .load(item.photoUrl)
                .thumbnail(Glide.with(holder.imageView)
                        .load(item.thumbnailUrl)
                        .apply(RequestOptions.centerCropTransform()))
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView)
    }

    override fun getPreloadItems(position: Int): List<PhotoState> = listOf(getItem(position))

    override fun getPreloadRequestBuilder(item: PhotoState): RequestBuilder<*>? =
            requestManager.load(item.photoUrl)
                    .thumbnail(requestManager.load(item.thumbnailUrl)
                            .apply(RequestOptions.centerCropTransform()))
                    .apply(RequestOptions.centerCropTransform())

    override fun onViewRecycled(holder: ImageViewHolder) {
        super.onViewRecycled(holder)
        Glide.with(holder.imageView)
                .clear(holder.imageView)
    }

    class ImageViewHolder(val imageView: AppCompatImageView) : ViewHolder(imageView)

}