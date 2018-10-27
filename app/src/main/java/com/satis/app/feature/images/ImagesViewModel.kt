package com.satis.app.feature.images

import androidx.fragment.app.FragmentActivity
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxViewModelFactory
import com.satis.app.BuildConfig
import com.satis.app.feature.images.data.FlickerProvider
import com.satis.app.utils.coroutines.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf
import kotlin.coroutines.CoroutineContext

class ImagesViewModel(
        initialState: ImagesState,
        private val flickerProvider: FlickerProvider,
        private val io: CoroutineDispatcher
) : BaseViewModel<ImagesState>(
        initialState = initialState,
        debugMode = BuildConfig.DEBUG
) {
    init {
        logStateChanges()
        fetchImages()
    }

    private fun fetchImages() {
        launch {
            val photos = withContext(io) {
                try {
                    flickerProvider.getRecentImages()
                } catch (t: Throwable) {
                    emptyList<PhotoState>()
                }
            }
            setState {
                copy(flickerPhotoUrls = photos)
            }
        }
    }

    companion object : MvRxViewModelFactory<ImagesState> {
        @JvmStatic
        override fun create(activity: FragmentActivity, state: ImagesState): BaseMvRxViewModel<ImagesState> =
                activity.get<ImagesViewModel> { parametersOf(state) }
    }
}