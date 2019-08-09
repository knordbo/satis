package com.satis.app.feature.images

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.satis.app.BuildConfig
import com.satis.app.feature.images.data.NATURE
import com.satis.app.feature.images.data.UnsplashRepository
import com.satis.app.utils.coroutines.BaseViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ImagesViewModel @AssistedInject constructor(
        @Assisted initialState: ImagesState,
        private val unsplashRepository: UnsplashRepository
) : BaseViewModel<ImagesState>(
        initialState = initialState,
        debugMode = BuildConfig.DEBUG
) {

    init {
        logStateChanges()
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
        fun create(initialState: ImagesState): ImagesViewModel
    }

    companion object : MvRxViewModelFactory<ImagesViewModel, ImagesState> {
        override fun create(viewModelContext: ViewModelContext, state: ImagesState): ImagesViewModel? {
            val fragment: ImagesFragment = (viewModelContext as FragmentViewModelContext).fragment()
            return fragment.createViewModel(state)
        }
    }
}