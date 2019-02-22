package com.satis.app.feature.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.satis.app.R
import kotlinx.android.synthetic.main.feature_playground.*

class PlaygroundFragment : BaseMvRxFragment() {

    private val playgroundViewModel: PlaygroundViewModel by activityViewModel()

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
            items.text = state.items.joinToString(separator = "\n") { it }
        }
    }

}