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
import com.satis.app.common.fragment.ReselectableFragment
import com.satis.app.feature.images.ui.ImagesAdapter
import kotlinx.android.synthetic.main.feature_images.*
import kotlinx.android.synthetic.main.feature_images.view.*

class ImagesFragment : BaseMvRxFragment(), ReselectableFragment {

    private val viewModel: ImagesViewModel by activityViewModel()
    private val adapter = ImagesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.feature_images, container, false).apply { initView() }

    override fun invalidate() {
        withState(viewModel) {
            adapter.submitList(it.flickerPhotoUrls)
        }
    }

    override fun onFragmentReselected() {
        images.smoothScrollToPosition(0)
        viewModel.onReselected()
    }

    private fun View.initView() {
        images.adapter = adapter
        images.layoutManager = GridLayoutManager(requireContext(), 2)
    }

}