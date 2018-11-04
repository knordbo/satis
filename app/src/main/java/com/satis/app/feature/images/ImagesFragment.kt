package com.satis.app.feature.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.peekandpop.shalskar.peekandpop.PeekAndPop
import com.satis.app.R
import com.satis.app.common.fragment.ReselectableFragment
import com.satis.app.feature.images.ui.ImagesAdapter
import kotlinx.android.synthetic.main.feature_images.*
import kotlinx.android.synthetic.main.feature_images.view.*

class ImagesFragment : BaseMvRxFragment(), ReselectableFragment {

    private val viewModel: ImagesViewModel by activityViewModel()
    private val adapter by lazy {
        ImagesAdapter(Glide.with(this), PeekAndPop.Builder(requireActivity())
                .peekLayout(R.layout.peek_image)
                .parentViewGroupToDisallowTouchEvents(view!!.images)
                .build())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.feature_images, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preloadSizeProvider = ViewPreloadSizeProvider<PhotoState>()
        val preloader = RecyclerViewPreloader<PhotoState>(
                Glide.with(this@ImagesFragment),
                adapter,
                preloadSizeProvider,
                IMAGE_PRELOAD_SIZE)

        images.adapter = adapter
        images.layoutManager = GridLayoutManager(requireContext(), COLUMNS)
        images.addOnScrollListener(preloader)

        val dividerDrawable = requireContext().getDrawable(R.drawable.divider)
        images.addItemDecoration(GridDividerItemDecoration(dividerDrawable, dividerDrawable, COLUMNS))
    }

    override fun invalidate() {
        withState(viewModel) {
            adapter.submitList(it.photoState)
        }
    }

    override fun onFragmentReselected() {
        images.smoothScrollToPosition(0)
        viewModel.onReselected()
    }

}

private const val IMAGE_PRELOAD_SIZE = 20
private const val COLUMNS = 3