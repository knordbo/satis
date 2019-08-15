package com.satis.app.feature.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.satis.app.R
import com.satis.app.utils.view.asyncText
import kotlinx.android.synthetic.main.feature_playground.*
import javax.inject.Inject

class PlaygroundFragment @Inject constructor(
        private val viewModelFactory: PlaygroundViewModel.Factory
): BaseMvRxFragment() {

    private val playgroundViewModel: PlaygroundViewModel by fragmentViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.feature_playground, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queryInput.doAfterTextChanged {
            playgroundViewModel.fetch(it.toString())
        }
    }

    override fun invalidate() {
        withState(playgroundViewModel) { state ->
            items.asyncText = state.items.joinToString(separator = "\n") { it }
        }
    }

    fun createViewModel(state: PlaygroundState) = viewModelFactory.create(state)

}