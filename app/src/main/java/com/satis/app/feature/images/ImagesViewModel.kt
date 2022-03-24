package com.satis.app.feature.images

import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.satis.app.feature.images.data.NATURE
import com.satis.app.feature.images.data.UnsplashRepository
import com.satis.app.utils.coroutines.BaseViewModel
import com.satis.app.utils.coroutines.viewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ImagesViewModel @AssistedInject constructor(
  @Assisted initialState: ImagesState,
  private val unsplashRepository: UnsplashRepository
) : BaseViewModel<ImagesState>(
  initialState = initialState
) {

  private val _events: MutableSharedFlow<Event> = MutableSharedFlow()
  val events: SharedFlow<Event> = _events.asSharedFlow()

  init {
    fetchPhotos()
    streamPhotos()
  }

  fun onReselected() {
    fetchPhotos()
    launch {
      _events.emit(Event.ScrollToTop)
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

  private fun fetchPhotos() {
    launch {
      try {
        unsplashRepository.fetchPhotos(NATURE)
      } catch (t: Throwable) {
        // ignored
      }
    }
  }

  interface Factory {
    fun createImagesViewModel(initialState: ImagesState): ImagesViewModel
  }

  @AssistedFactory
  interface FactoryImpl : Factory

  companion object : MavericksViewModelFactory<ImagesViewModel, ImagesState> {
    override fun create(viewModelContext: ViewModelContext, state: ImagesState): ImagesViewModel {
      return viewModelContext.viewModelFactory<Factory>().createImagesViewModel(state)
    }
  }
}

sealed class Event {
  object ScrollToTop : Event()
  object Initial : Event()
}