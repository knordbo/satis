package com.satis.app.feature.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.fragmentViewModel
import com.satis.app.common.fragment.BaseFragment
import com.satis.app.common.theme.AppTheme
import dev.chrisbanes.accompanist.coil.CoilImage
import javax.inject.Inject

class ImagesFragment @Inject constructor(
  private val viewModelFactory: ImagesViewModel.Factory,
) : BaseFragment(), ImagesViewModel.Factory by viewModelFactory {

  private val imagesViewModel: ImagesViewModel by fragmentViewModel()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return ComposeView(inflater.context).apply {
      layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
      setContent {
        AppTheme {
          val state = imagesViewModel.stateFlow.collectAsState(ImagesState())
          LazyColumn {
            val items = state.value.photoState.toPhotoStateItems()
            items(items) { rows ->
              Row(
                modifier = Modifier.padding(bottom = (if (rows === items.lastOrNull()) 0 else 2).dp)
              ) {
                rows.forEach { item ->
                  if (item.row > 0) {
                    Spacer(modifier = Modifier.width(2.dp))
                  }
                  CoilImage(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                      .aspectRatio(1f)
                      .weight(1f)
                      .clickable {
                        findNavController().navigate(ImagesFragmentDirections.actionImagesToImage(item.photoState, item.photoState.description.orEmpty()))
                      },
                    data = item.photoState.photoUrl,
                  )
                }
              }
            }
          }
        }
      }
    }
  }

}

private fun List<PhotoState>.toPhotoStateItems(): List<List<PhotoStateItem>> = mapIndexed { index, photoState ->
  PhotoStateItem(photoState = photoState, row = index % COLUMNS, column = index / COLUMNS)
}.groupBy { it.column }.map { it.value }.toList()

data class PhotoStateItem(val photoState: PhotoState, val row: Int, val column: Int)

private const val COLUMNS = 3