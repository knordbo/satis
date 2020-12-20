package com.satis.app.feature.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit.Companion.Sp
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.satis.app.common.theme.AppTheme
import javax.inject.Inject

class PlaygroundFragment @Inject constructor(
  private val viewModelFactory: PlaygroundViewModel.Factory
) : Fragment(), MavericksView, PlaygroundViewModel.Factory by viewModelFactory {

  private val playgroundViewModel: PlaygroundViewModel by fragmentViewModel()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return ComposeView(inflater.context).apply {
      layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
      setContent {
        AppTheme {
          Column(
            modifier = Modifier.padding(Dp(16f))
          ) {
            val queryState = remember { mutableStateOf(TextFieldValue()) }
            TextField(
              value = queryState.value,
              modifier = Modifier.padding(bottom = Dp(8f)),
              onValueChange = { value ->
                queryState.value = value
                playgroundViewModel.fetch(value.text)
              })
            val state = playgroundViewModel.stateFlow.collectAsState(PlaygroundState())
            LazyColumn {
              items(state.value.items) { item ->
                Text(
                  text = item,
                  style = TextStyle(
                    fontSize = Sp(12)
                  ),
                  color = MaterialTheme.colors.onSurface
                )
              }
            }
          }
        }
      }
    }
  }

  override fun invalidate() = Unit
}