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
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.satis.app.R
import com.satis.app.common.navigation.NavigationReselection
import com.satis.app.databinding.FeatureCardsBinding
import com.satis.app.feature.cards.ui.CardAdapter
import com.satis.app.utils.view.disableChangeAnimations
import javax.inject.Inject

class CardFragment @Inject constructor(
        private val viewModelFactory: CardViewModel.Factory,
        private val navigationReselection: NavigationReselection
) : BaseMvRxFragment() {

    private val cardViewModel: CardViewModel by fragmentViewModel()
    private val cardsAdapter by lazy {
        CardAdapter(
                onLikeClicked = cardViewModel::like,
                onDislikeClicked = cardViewModel::dislike,
                onSwiped = cardViewModel::removeCard
        )
    }

    private lateinit var binding: FeatureCardsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FeatureCardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cards.adapter = cardsAdapter
        binding.cards.disableChangeAnimations()

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, START or END) {
            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                if (viewHolder.adapterPosition != NO_POSITION) {
                    cardsAdapter.onSwiped(viewHolder.adapterPosition)
                }
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean = false
        }).attachToRecyclerView(binding.cards)

        navigationReselection.addReselectionListener(viewLifecycleOwner, R.id.home) {
            binding.cards.smoothScrollToPosition(0)
        }
    }

    override fun invalidate() = withState(cardViewModel) { cardState ->
        cardsAdapter.submitList(cardState.cards)
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

    fun createViewModel(state: CardState): CardViewModel = viewModelFactory.create(state)

}

private const val ADD_CARD_FRAGMENT_TAG = "ADD_CARD_FRAGMENT"