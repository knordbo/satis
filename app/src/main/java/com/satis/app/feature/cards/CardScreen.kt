package com.satis.app.feature.cards

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.satis.app.R

@Composable
fun CardScreen(viewModel: CardViewModel = hiltViewModel()) {
  val state = viewModel.state.collectAsState(CardState())
  Scaffold(topBar = {
    CardAppBar(viewModel, state)
  }) { paddingValues ->
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
      items(state.value.cards) { card ->
        Card(
          elevation = 2.dp,
          modifier = Modifier
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .fillParentMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
          ) {
            CardText(
              text = card.title,
              fontSize = 20.sp,
              modifier = Modifier.padding(bottom = 8.dp)
            )
            CardText(
              text = card.message,
              fontSize = 18.sp,
              modifier = Modifier.padding(bottom = 8.dp)
            )
            Row {
              CardText(
                text = card.likes.toString(),
                fontSize = 20.sp,
                modifier = Modifier.padding(end = 8.dp),
              )
              Image(
                imageVector = Icons.Default.ThumbUp,
                contentDescription = null,
                colorFilter = ColorFilter.tint(if (card.hasLiked) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface),
                modifier = Modifier.clickable {
                  viewModel.like(card)
                },
              )
              CardText(
                text = card.dislikes.toString(),
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
              )
              Image(
                imageVector = Icons.Default.ThumbDown,
                contentDescription = null,
                colorFilter = ColorFilter.tint(if (card.hasDisliked) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface),
                modifier = Modifier.clickable {
                  viewModel.dislike(card)
                },
              )
              Spacer(modifier = Modifier.weight(1f))
              Image(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                modifier = Modifier.clickable {
                  viewModel.removeCard(card)
                },
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun CardText(text: String, fontSize: TextUnit, modifier: Modifier) = Text(
  text = text,
  fontSize = fontSize,
  modifier = modifier,
  color = MaterialTheme.colors.onSurface,
)

@Composable
private fun CardAppBar(viewModel: CardViewModel, state: State<CardState>) {
  val context = LocalContext.current

  val creatingCardEvent = state.value.creatingCardEvent

  val closeDialog = {
    viewModel.closeCreateDialog()
  }

  LaunchedEffect(creatingCardEvent) {
    if (creatingCardEvent != CreatingCardEvent.None) {
      when (creatingCardEvent) {
        CreatingCardEvent.NoTitle -> {
          Toast.makeText(context, R.string.card_message_no_title, Toast.LENGTH_SHORT).show()
        }
        CreatingCardEvent.Success -> {
          closeDialog()
        }
        CreatingCardEvent.None -> Unit
      }
      viewModel.addCardEventHandled()
    }
  }

  TopAppBar(
    title = {
      Text(stringResource(id = R.string.home))
    },
    actions = {
      Text(
        text = stringResource(id = R.string.card_add_header).uppercase(),
        modifier = Modifier
          .padding(8.dp)
          .clickable {
            viewModel.openCreateDialog()
          },
      )
      if (state.value.creatingCardDialogOpen) {
        OpenDialog(viewModel = viewModel, onDismiss = closeDialog, state = state)
      }
    }
  )
}

@Composable
private fun OpenDialog(viewModel: CardViewModel, onDismiss: () -> Unit, state: State<CardState>) {
  Dialog(
    onDismissRequest = onDismiss::invoke,
    content = {
      Card(
        modifier = Modifier.padding(8.dp),
      ) {
        Column {
          Text(
            text = stringResource(id = R.string.card_add_header),
            modifier = Modifier.padding(16.dp),
            fontSize = 20.sp,
          )

          TextField(
            modifier = Modifier.padding(16.dp),
            value = state.value.creatingCard.title,
            onValueChange = {
              viewModel.addCardTitleChanged(it)
            },
            label = { Text(stringResource(id = R.string.card_title_hint)) }
          )

          TextField(
            modifier = Modifier.padding(16.dp),
            value = state.value.creatingCard.message,
            onValueChange = {
              viewModel.addCardMessageChanged(it)
            },
            label = { Text(stringResource(id = R.string.card_message_hint)) }
          )

          Row(modifier = Modifier.align(Alignment.End)) {
            Button(
              modifier = Modifier.padding(16.dp),
              onClick = {
                viewModel.addCard()
              }
            ) {
              Text(text = stringResource(id = R.string.card_add).uppercase())
            }
          }
        }
      }
    },
  )
}