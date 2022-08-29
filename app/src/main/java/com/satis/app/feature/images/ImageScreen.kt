package com.satis.app.feature.images

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import coil.compose.AsyncImage
import kotlin.math.roundToInt

@Composable
fun ImageScreen(photoUrl: String, description: String?) {
  Scaffold(topBar = {
    TopAppBar(title = {
      Text(description.orEmpty())
    })
  }) { paddingValues ->
    val zoom = remember { mutableStateOf(1f) }
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }
    BoxWithConstraints(
      modifier = Modifier
        .clip(RectangleShape)
        .graphicsLayer(
          scaleX = zoom.value,
          scaleY = zoom.value,
        )
        .pointerInput(Unit) {
          detectTransformGestures(
            onGesture = { _, pan, gestureZoom, _ ->
              setZoom(zoom, gestureZoom)
              offsetX.value = offsetX.value + pan.x
              offsetY.value = offsetY.value + pan.y
            }
          )
        }
    ) {
      AsyncImage(
        model = photoUrl,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
          .padding(paddingValues)
          .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
          .fillMaxHeight(),
        alignment = Alignment.Center,
        onSuccess = { success ->
          setZoom(zoom, constraints.maxHeight / success.result.drawable.intrinsicHeight.toFloat())
        }
      )
    }
  }
}

private fun setZoom(
  zoom: MutableState<Float>,
  newValue: Float,
) {
  zoom.value = (zoom.value * newValue).coerceIn(1f, 10f)
}
