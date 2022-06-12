package com.satis.app.feature.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satis.app.feature.images.data.NATURE
import com.satis.app.feature.images.data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ImagesViewModel @Inject constructor(
  private val unsplashRepository: UnsplashRepository,
) : ViewModel(), CoroutineScope {

  override val coroutineContext: CoroutineContext
    get() = viewModelScope.coroutineContext

  private val _state: MutableStateFlow<ImagesState> = MutableStateFlow(value = ImagesState())
  val state: StateFlow<ImagesState> = _state.asStateFlow()

  fun load() {
    fetchPhotos()
    streamPhotos()
  }

  fun onReselected() {
    fetchPhotos()
    setState {
      copy(scrollEvent = ScrollEvent.ScrollToTop)
    }
  }

  fun scrollEventHandled() {
    setState {
      copy(scrollEvent = ScrollEvent.None)
    }
  }

  private fun streamPhotos() {
    launch {
      unsplashRepository.streamPhotos(NATURE).collect { photos ->
        setState {
          copy(photoState = photos)
        }
      }
    }
  }

  private fun setState(update: ImagesState.() -> ImagesState) {
    _state.value = _state.value.update()
  }

  private fun fetchPhotos() {
    launch {
      try {
        unsplashRepository.fetchPhotos(NATURE)
      } catch (t: Throwable) {
        // ignored
      }
    }
  }
}