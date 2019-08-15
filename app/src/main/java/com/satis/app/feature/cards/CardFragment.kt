package com.satis.app.feature.cards

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.END
import androidx.recyclerview.widget.ItemTouchHelper.START
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.satis.app.R
import com.satis.app.common.navigation.NavigationViewModel
import com.satis.app.common.navigation.Tab.HOME
import com.satis.app.feature.cards.ui.AddCardView
import com.satis.app.feature.cards.ui.CardAdapter
import com.satis.app.utils.view.disableChangeAnimations
import kotlinx.android.synthetic.main.feature_cards.*
import javax.inject.Inject

class CardFragment @Inject constructor(
        private val viewModelFactory: CardViewModel.Factory
) : BaseMvRxFragment() {

    private val cardViewModel: CardViewModel by fragmentViewModel()
    private val navigationViewModel: NavigationViewModel by activityViewModel()
    private val cardsAdapter by lazy {
        CardAdapter(
                onLikeClicked = { card ->
                    cardViewModel.like(card)
                },
                onDislikeClicked = { card ->
                    cardViewModel.dislike(card)
                },
                onSwiped = { card ->
                    cardViewModel.removeCard(card)
                }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.feature_cards, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cards.adapter = cardsAdapter
        cards.disableChangeAnimations()

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, START or END) {
            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                if (viewHolder.adapterPosition != NO_POSITION) {
                    cardsAdapter.onSwiped(viewHolder.adapterPosition)
                }
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean = false
        }).attachToRecyclerView(cards)
    }

    override fun invalidate() = withState(cardViewModel, navigationViewModel) { cardState, navigationState ->
        cardsAdapter.submitList(cardState.cards)
        if (navigationState.reselectedTab == HOME) {
            navigationViewModel.tabReselectedHandled()
            cards.smoothScrollToPosition(0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.cards_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.cardAdd -> {
            AddCardView.createDialog(requireContext(), cardViewModel::addCard).show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    fun createViewModel(state: CardState): CardViewModel = viewModelFactory.create(state)

}