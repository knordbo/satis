package com.satis.app.feature.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.satis.app.common.fragment.BaseFragment
import com.satis.app.databinding.FeaturePlaygroundBinding
import com.satis.app.utils.view.asyncText
import javax.inject.Inject

class PlaygroundFragment @Inject constructor(
        private val viewModelFactory: PlaygroundViewModel.Factory
) : BaseFragment<FeaturePlaygroundBinding>(), PlaygroundViewModel.Factory by viewModelFactory {

    private val playgroundViewModel: PlaygroundViewModel by fragmentViewModel()

    override val bind: (LayoutInflater, ViewGroup?, Boolean) -> FeaturePlaygroundBinding? =
            FeaturePlaygroundBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.queryInput.doAfterTextChanged {
            playgroundViewModel.fetch(it.toString())
        }
    }

    override fun invalidate() {
        withState(playgroundViewModel) { state ->
            binding.items.asyncText = state.items.joinToString(separator = "\n") { it }
        }
    }

}