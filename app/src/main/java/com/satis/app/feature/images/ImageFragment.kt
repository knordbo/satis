package com.satis.app.feature.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.satis.app.R
import kotlinx.android.synthetic.main.feature_image.*

class ImageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.feature_image, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photoState = ImageFragmentArgs.fromBundle(arguments).photo

        (activity as? AppCompatActivity)?.supportActionBar?.title = photoState.description

        Glide.with(this)
                .load(photoState.photoUrl)
                .thumbnail(Glide.with(this)
                        .load(photoState.thumbnailUrl)
                        .apply(RequestOptions.centerCropTransform()))
                .apply(RequestOptions.centerCropTransform())
                .into(image)
    }

}