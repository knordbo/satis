package com.satis.app.feature.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.peekandpop.shalskar.peekandpop.PeekAndPop
import com.satis.app.common.navigation.NavigationViewModel
import com.satis.app.R
import com.satis.app.common.navigation.Tab.IMAGES
import com.satis.app.feature.images.ui.ImagesAdapter
import kotlinx.android.synthetic.main.feature_images.*
import kotlinx.android.synthetic.main.feature_images.view.*
import javax.inject.Inject

class ImagesFragment @Inject constructor(
        private val viewModelFactory: ImagesViewModel.Factory
) : BaseMvRxFragment() {

    private val navigationViewModel: NavigationViewModel by activityViewModel()
    private val imagesViewModel: ImagesViewModel by fragmentViewModel()
    private val adapter by lazy {
        ImagesAdapter(
                requestManager = Glide.with(this),
                peekAndPop = PeekAndPop.Builder(requireActivity())
                        .peekLayout(R.layout.peek_image)
                        .parentViewGroupToDisallowTouchEvents(view!!.images)
                        .build(),
                imageClicked = { photostate ->
                    findNavController().navigate(ImagesFragmentDirections.actionImagesToImage(photostate))
                }
        )
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
        withState(imagesViewModel, navigationViewModel) { imageState, navigationState ->
            adapter.submitList(imageState.photoState)
            if (navigationState.reselectedTab == IMAGES) {
                navigationViewModel.tabReselectedHandled()
                images.smoothScrollToPosition(0)
                imagesViewModel.onReselected()
            }
        }
    }

    fun createViewModel(state: ImagesState) = viewModelFactory.create(state)

}

private const val IMAGE_PRELOAD_SIZE = 20
private const val COLUMNS = 3