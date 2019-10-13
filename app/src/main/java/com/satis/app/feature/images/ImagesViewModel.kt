package com.satis.app.feature.images

import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.satis.app.feature.images.data.NATURE
import com.satis.app.feature.images.data.UnsplashRepository
import com.satis.app.utils.coroutines.BaseViewModel
import com.satis.app.utils.coroutines.viewModelFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ImagesViewModel @AssistedInject constructor(
        @Assisted initialState: ImagesState,
        private val unsplashRepository: UnsplashRepository
) : BaseViewModel<ImagesState>(
        initialState = initialState
) {

    init {
        fetchPhotos()
        streamPhotos()
    }

    fun onReselected() {
        fetchPhotos()
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

    @AssistedInject.Factory
    interface Factory {
        fun createImagesViewModel(initialState: ImagesState): ImagesViewModel
    }

    companion object : MvRxViewModelFactory<ImagesViewModel, ImagesState> {
        override fun create(viewModelContext: ViewModelContext, state: ImagesState): ImagesViewModel? {
            return viewModelContext.viewModelFactory<Factory>().createImagesViewModel(state)
        }
    }
}