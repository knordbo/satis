package com.satis.app.feature.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.fragmentViewModel
import com.satis.app.R
import com.satis.app.common.fragment.BaseFragment
import com.satis.app.common.theme.AppTheme
import javax.inject.Inject

class CardFragment @Inject constructor(
  private val viewModelFactory: CardViewModel.Factory,
) : BaseFragment(), CardViewModel.Factory by viewModelFactory {

  private val cardViewModel: CardViewModel by fragmentViewModel()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return ComposeView(inflater.context).apply {
      layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
      setContent {
        val state = cardViewModel.stateFlow.collectAsState(CardState())
        AppTheme {
          LazyColumn {
            items(state.value.cards) { card ->
              Card(
                elevation = 2.dp,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp).fillParentMaxWidth()
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
                      painter = painterResource(R.drawable.ic_thumb_up),
                      contentDescription = null,
                      colorFilter = tint(if (card.hasLiked) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface),
                      modifier = Modifier.clickable {
                        cardViewModel.like(card)
                      },
                    )
                    CardText(
                      text = card.dislikes.toString(),
                      fontSize = 20.sp,
                      modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    )
                    Image(
                      painter = painterResource(R.drawable.ic_thumb_down),
                      contentDescription = null,
                      colorFilter = tint(if (card.hasDisliked) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface),
                      modifier = Modifier.clickable {
                        cardViewModel.dislike(card)
                      },
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                      painter = painterResource(R.drawable.ic_delete),
                      contentDescription = null,
                      colorFilter = tint(MaterialTheme.colors.onSurface),
                      modifier = Modifier.clickable {
                        cardViewModel.removeCard(card)
                      },
                    )
                  }
                }
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    super.onCreateOptionsMenu(menu, inflater)
    inflater.inflate(R.menu.cards_menu, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
    R.id.cardAdd -> {
      showAddCardFragment()
      true
    }
    else -> super.onOptionsItemSelected(item)
  }

  private fun showAddCardFragment() {
    val fragment = AddCardFragment()
    fragment.setTargetFragment(this@CardFragment, 0)
    fragment.show(parentFragmentManager, ADD_CARD_FRAGMENT_TAG)
  }

}

private const val ADD_CARD_FRAGMENT_TAG = "ADD_CARD_FRAGMENT"