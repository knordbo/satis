package com.satis.app.feature.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.satis.app.R
import com.satis.app.common.fragment.BaseFragment
import com.satis.app.common.navigation.NavigationReselection
import com.satis.app.databinding.FeatureImagesBinding
import com.satis.app.feature.images.ui.ImagesAdapter
import com.satis.app.utils.context.requireDrawable
import com.satis.app.utils.view.disableChangeAnimations
import javax.inject.Inject

class ImagesFragment @Inject constructor(
    private val viewModelFactory: ImagesViewModel.Factory,
    private val navigationReselection: NavigationReselection
) : BaseFragment<FeatureImagesBinding>(), ImagesViewModel.Factory by viewModelFactory {

  private val imagesViewModel: ImagesViewModel by fragmentViewModel()
  private val imageViewPreloadSizeProvider = ViewPreloadSizeProvider<PhotoState>()
  private val adapter by lazy {
    ImagesAdapter(
        requestManager = Glide.with(this),
        imageViewPreloadSizeProvider = imageViewPreloadSizeProvider,
        imageClicked = { photo ->
          findNavController().navigate(ImagesFragmentDirections.actionImagesToImage(photo, photo.description.orEmpty()))
        }
    )
  }

  override val bind: (LayoutInflater, ViewGroup?, Boolean) -> FeatureImagesBinding? =
      FeatureImagesBinding::inflate

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val preloader = RecyclerViewPreloader(
        Glide.with(this@ImagesFragment),
        adapter,
        imageViewPreloadSizeProvider,
        IMAGE_PRELOAD_SIZE
    )

    binding.images.adapter = adapter
    binding.images.layoutManager = GridLayoutManager(requireContext(), COLUMNS)
    binding.images.addOnScrollListener(preloader)
    binding.images.disableChangeAnimations()

    val dividerDrawable = requireContext().requireDrawable(R.drawable.divider)
    binding.images.addItemDecoration(GridDividerItemDecoration(dividerDrawable, dividerDrawable, COLUMNS))

    navigationReselection.addReselectionListener(viewLifecycleOwner, R.id.images) {
      binding.images.smoothScrollToPosition(0)
      imagesViewModel.onReselected()
    }
  }

  override fun onDestroyView() {
    binding.images.adapter = null
    super.onDestroyView()
  }

  override fun invalidate() {
    withState(imagesViewModel) { imageState ->
      adapter.submitList(imageState.photoState)
    }
  }

}

private const val IMAGE_PRELOAD_SIZE = 20
private const val COLUMNS = 3