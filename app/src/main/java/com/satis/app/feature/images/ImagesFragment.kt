package com.satis.app.feature.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.compose.AsyncImage
import com.satis.app.R
import com.satis.app.common.navigation.NavigationReselection
import com.satis.app.common.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImagesFragment : Fragment() {

  @Inject lateinit var navigationReselection: NavigationReselection

  private val imagesViewModel: ImagesViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (savedInstanceState == null) {
      imagesViewModel.load()
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    return ComposeView(inflater.context).apply {
      layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
      setContent {
        AppTheme {
          val state = imagesViewModel.state.collectAsState(ImagesState())
          val lazyListState = rememberLazyListState()

          val scrollEvent = state.value.scrollEvent
          LaunchedEffect(scrollEvent) {
            if (scrollEvent == ScrollEvent.ScrollToTop) {
              lazyListState.scrollToItem(0)
            }
            if (scrollEvent != ScrollEvent.None) {
              imagesViewModel.scrollEventHandled()
            }
          }

          LazyColumn(state = lazyListState) {
            val items = state.value.photoState.toPhotoStateItems()
            items(items) { rows ->
              Row(
                modifier = Modifier.padding(bottom = (if (rows === items.lastOrNull()) 0 else 2).dp)
              ) {
                rows.forEach { item ->
                  if (item.row > 0) {
                    Spacer(modifier = Modifier.width(2.dp))
                  }
                  AsyncImage(
                    model = item.photoState.photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                      .aspectRatio(1f)
                      .weight(1f)
                      .clickable {
                        findNavController().navigate(
                          ImagesFragmentDirections.actionImagesToImage(
                            item.photoState,
                            item.photoState.description.orEmpty()
                          )
                        )
                      },
                  )
                }
              }
            }
          }
        }
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    navigationReselection.addReselectionListener(viewLifecycleOwner, R.id.images) {
      imagesViewModel.onReselected()
    }
  }

}

private fun List<PhotoState>.toPhotoStateItems(): List<List<PhotoStateItem>> =
  mapIndexed { index, photoState ->
    PhotoStateItem(photoState = photoState, row = index % COLUMNS, column = index / COLUMNS)
  }.groupBy { it.column }.map { it.value }.toList()

data class PhotoStateItem(val photoState: PhotoState, val row: Int, val column: Int)

private const val COLUMNS = 3
