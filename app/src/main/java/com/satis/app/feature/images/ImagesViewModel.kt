package com.satis.app.feature.images

import androidx.fragment.app.FragmentActivity
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxViewModelFactory
import com.satis.app.BuildConfig
import com.satis.app.feature.images.data.NATURE
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
        fetchPhotos()
        streamPhotos()
    }

    fun onReselected() {
        fetchPhotos()
    }

    private fun streamPhotos() {
        launch {
            for (photos in unsplashProvider.streamPhotos(NATURE)) {
                setState {
                    copy(photoState = photos)
                }
            }
        }
    }

    private fun fetchPhotos() {
        launch(io) {
            try {
                unsplashProvider.fetchPhotos(NATURE)
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