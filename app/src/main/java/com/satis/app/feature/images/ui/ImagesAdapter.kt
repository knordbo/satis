package com.satis.app.feature.images.ui

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.api.clear
import coil.api.load
import com.satis.app.R
import com.satis.app.feature.images.PhotoState
import com.satis.app.feature.images.ui.ImagesAdapter.ImageViewHolder
import com.satis.app.utils.view.layoutInflater

class ImagesAdapter(
  private val imageClicked: (PhotoState) -> Unit
) : ListAdapter<PhotoState, ImageViewHolder>(Differ) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
    val view = parent.layoutInflater.inflate(R.layout.image_item, parent, false)
    return ImageViewHolder(view as AppCompatImageView)
  }

  override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
    val item = getItem(position)
    with(holder.imageView) {
      load(item.photoUrl)
      setOnClickListener {
        imageClicked(item)
      }
    }
  }

  override fun onViewRecycled(holder: ImageViewHolder) {
    super.onViewRecycled(holder)
    holder.imageView.clear()
  }

  class ImageViewHolder(val imageView: AppCompatImageView) : ViewHolder(imageView)

  private object Differ : DiffUtil.ItemCallback<PhotoState>() {
    override fun areItemsTheSame(oldItem: PhotoState, newItem: PhotoState): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: PhotoState, newItem: PhotoState): Boolean = oldItem == newItem
  }

}