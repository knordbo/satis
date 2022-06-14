package com.satis.app.feature.images

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.satis.app.R

@Composable
fun ImagesScreen(
  navController: NavController,
) {
  ImagesScreen(navController = navController, imagesViewModel = hiltViewModel())
}

@Composable
private fun ImagesScreen(
  navController: NavController,
  imagesViewModel: ImagesViewModel,
) {
  Scaffold(topBar = {
    TopAppBar(title = {
      Text(stringResource(id = R.string.images))
    })
  }) {
    val state = imagesViewModel.state.collectAsState(ImagesState())
    val lazyListState = rememberLazyListState()

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
                  var route = "image?photoUrl=${item.photoState.photoUrl}"
                  if (item.photoState.description != null) {
                    route = "$route&description=${item.photoState.description}"
                  }
                  navController.navigate(route)
                },
            )
          }
        }
      }
    }
  }
}

private fun List<PhotoState>.toPhotoStateItems(): List<List<PhotoStateItem>> =
  mapIndexed { index, photoState ->
    PhotoStateItem(photoState = photoState, row = index % COLUMNS, column = index / COLUMNS)
  }.groupBy { it.column }.map { it.value }.toList()

private data class PhotoStateItem(val photoState: PhotoState, val row: Int, val column: Int)

private const val COLUMNS = 3