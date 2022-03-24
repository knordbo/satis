package com.satis.app.feature.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import coil.compose.AsyncImage
import com.airbnb.mvrx.fragmentViewModel
import com.satis.app.common.fragment.BaseFragment
import com.satis.app.common.theme.AppTheme
import com.satis.app.feature.images.ImagesState
import com.satis.app.feature.images.ImagesViewModel
import javax.inject.Inject

class PlaygroundFragment @Inject constructor(
  private val viewModelFactory: ImagesViewModel.Factory
) : BaseFragment(), ImagesViewModel.Factory by viewModelFactory {

  private val imagesViewModel: ImagesViewModel by fragmentViewModel()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return ComposeView(inflater.context).apply {
      layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
      setContent {
        AppTheme {
          val state = imagesViewModel.stateFlow.collectAsState(ImagesState())
          LazyVerticalGrid(
            columns = GridCells.Fixed(3),
          ) {
            items(state.value.photoState) { photo ->
              AsyncImage(
                model = photo.photoUrl,
                contentScale = ContentScale.Crop,
                contentDescription = photo.description,
                modifier = Modifier.aspectRatio(1f),
              )
            }
          }
        }
      }
    }
  }
}