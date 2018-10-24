package com.satis.app.feature.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.satis.app.R
import com.satis.app.feature.images.ui.ImagesAdapter
import kotlinx.android.synthetic.main.feature_images.view.*

class ImagesFragment : BaseMvRxFragment() {

    private val viewModel: ImagesViewModel by activityViewModel()
    private val adapter = ImagesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.feature_images, container, false).apply { initView() }

    override fun invalidate() {
        withState(viewModel) {
            adapter.submitList(it.flickerPhotoUrls)
        }
    }

    private fun View.initView() {
        rv.adapter = adapter
        rv.layoutManager = GridLayoutManager(requireContext(), 2)
    }

}