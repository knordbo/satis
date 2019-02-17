package com.satis.app.feature.images.ui

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions.circleCropTransform
import com.peekandpop.shalskar.peekandpop.PeekAndPop
import com.satis.app.R
import com.satis.app.feature.images.PhotoState
import com.satis.app.feature.images.ui.ImagesAdapter.ImageViewHolder
import com.satis.app.utils.view.layoutInflater
import kotlinx.android.synthetic.main.peek_image.view.*

class ImagesAdapter(
        private val requestManager: RequestManager,
        private val peekAndPop: PeekAndPop,
        private val imageClicked: (PhotoState) -> Unit
) : ListAdapter<PhotoState, ImageViewHolder>(object : DiffUtil.ItemCallback<PhotoState>() {
    override fun areItemsTheSame(oldItem: PhotoState, newItem: PhotoState): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: PhotoState, newItem: PhotoState): Boolean = oldItem == newItem
}), ListPreloader.PreloadModelProvider<PhotoState> {

    init {
        peekAndPop.setOnGeneralActionListener(object : PeekAndPop.OnGeneralActionListener {
            override fun onPop(view: View, position: Int) {}
            override fun onPeek(view: View, position: Int) {
                val item = getItem(position)
                with(peekAndPop.peekView) {
                    username.text = item.user.username
                    Glide.with(peekAndPop.peekView)
                            .load(item.user.userAvatar)
                            .apply(circleCropTransform())
                            .into(avatar)
                    image.load(item)
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
            ImageViewHolder(parent.layoutInflater.inflate(R.layout.image_item, parent, false) as AppCompatImageView)

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.imageView) {
            load(item)
            peekAndPop.addLongClickView(this, position)
            setOnClickListener {
                imageClicked(item)
            }
        }
    }

    override fun getPreloadItems(position: Int): List<PhotoState> = listOf(getItem(position))

    override fun getPreloadRequestBuilder(item: PhotoState): RequestBuilder<*>? =
            requestManager.load(item.photoUrl)
                    .thumbnail(requestManager.load(item.thumbnailUrl)
                            .centerCrop())
                    .centerCrop()

    override fun onViewRecycled(holder: ImageViewHolder) {
        super.onViewRecycled(holder)
        Glide.with(holder.imageView)
                .clear(holder.imageView)
    }

    private fun ImageView.load(photoState: PhotoState) {
        Glide.with(this)
                .load(photoState.photoUrl)
                .thumbnail(Glide.with(this)
                        .load(photoState.thumbnailUrl)
                        .centerCrop())
                .centerCrop()
                .into(this)
    }

    class ImageViewHolder(val imageView: AppCompatImageView) : ViewHolder(imageView)

}