package com.satis.app.feature.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.satis.app.common.fragment.BaseViewBindingFragment
import com.satis.app.databinding.FeatureImageBinding
import javax.inject.Inject

class ImageFragment @Inject constructor() : BaseViewBindingFragment<FeatureImageBinding>() {

  override val bind: (LayoutInflater, ViewGroup?, Boolean) -> FeatureImageBinding? =
    FeatureImageBinding::inflate

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val photoState = ImageFragmentArgs.fromBundle(requireArguments()).photo
    binding.image.load(photoState.photoUrl)
  }

}