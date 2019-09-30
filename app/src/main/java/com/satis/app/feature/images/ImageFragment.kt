package com.satis.app.feature.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.satis.app.databinding.FeatureImageBinding
import javax.inject.Inject

class ImageFragment @Inject constructor() : Fragment() {

    private lateinit var binding: FeatureImageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FeatureImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photoState = ImageFragmentArgs.fromBundle(requireArguments()).photo

        Glide.with(this)
                .load(photoState.photoUrl)
                .thumbnail(Glide.with(this)
                        .load(photoState.thumbnailUrl)
                        .centerCrop())
                .centerCrop()
                .into(binding.image)
    }

}