package com.satis.app.feature.images

import androidx.fragment.app.FragmentActivity
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxViewModelFactory
import com.satis.app.BuildConfig
import com.satis.app.feature.images.data.UnsplashProvider
import com.satis.app.utils.coroutines.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class ImagesViewModel(
        initialState: ImagesState,
        private val unsplashProvider: UnsplashProvider,
        private val io: CoroutineDispatcher
) : BaseViewModel<ImagesState>(
        initialState = initialState,
        debugMode = BuildConfig.DEBUG
) {

    init {
        logStateChanges()
        fetchImages()
        streamPopularImages()
    }

    fun onReselected() {
        fetchImages()
    }

    private fun streamPopularImages() {
        launch {
            for (photos in unsplashProvider.streamCuratedPhotos()) {
                setState {
                    copy(photoState = photos)
                }
            }
        }
    }

    private fun fetchImages() {
        launch(io) {
            try {
                unsplashProvider.fetchCuratedPhotos()
            } catch (t: Throwable) {
                // ignored
            }
        }
    }

    companion object : MvRxViewModelFactory<ImagesState> {
        @JvmStatic
        override fun create(activity: FragmentActivity, state: ImagesState): BaseMvRxViewModel<ImagesState> =
                activity.get<ImagesViewModel> { parametersOf(state) }
    }
}