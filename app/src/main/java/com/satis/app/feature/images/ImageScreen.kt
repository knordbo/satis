package com.satis.app.feature.images

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun ImageScreen(photoUrl: String, description: String?) {
  Scaffold(topBar = {
    TopAppBar(title = {
      Text(description.orEmpty())
    })
  }) { paddingValues ->
    AsyncImage(
      model = photoUrl,
      contentDescription = null,
      contentScale = ContentScale.Crop,
      modifier = Modifier.padding(paddingValues)
    )
  }
}