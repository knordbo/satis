package com.satis.app.feature.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.satis.app.common.theme.AppTheme
import com.satis.app.feature.images.ImagesState
import com.satis.app.feature.images.ImagesViewModel
import dev.chrisbanes.accompanist.coil.CoilImage
import javax.inject.Inject

class PlaygroundFragment @Inject constructor(
  private val viewModelFactory: ImagesViewModel.Factory
) : Fragment(), MavericksView, ImagesViewModel.Factory by viewModelFactory {

  private val imagesViewModel: ImagesViewModel by fragmentViewModel()

  @ExperimentalFoundationApi
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return ComposeView(inflater.context).apply {
      layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
      setContent {
        AppTheme {
          val state = imagesViewModel.stateFlow.collectAsState(ImagesState())
          LazyVerticalGrid(
            cells = GridCells.Fixed(3),
          ) {
            items(state.value.photoState) { photo ->
              CoilImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(1f),
                data = photo.photoUrl,
              )
            }
          }
        }
      }
    }
  }

  override fun invalidate() = Unit
}