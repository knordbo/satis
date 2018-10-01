package com.satis.app.feature.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.END
import androidx.recyclerview.widget.ItemTouchHelper.START
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.satis.app.BuildConfig
import com.satis.app.R
import com.satis.app.common.Prefs
import com.satis.app.feature.cards.ui.AddCardView
import com.satis.app.feature.cards.ui.CardAdapter
import com.satis.app.feature.cards.ui.CardItemView
import com.satis.app.utils.view.disableChangeAnimations
import kotlinx.android.synthetic.main.feature_cards.view.*
import org.koin.android.ext.android.inject

class CardFragment : BaseMvRxFragment(), OnChildAttachStateChangeListener {

    private val viewModel: CardViewModel by fragmentViewModel()
    private val cardsAdapter = CardAdapter()
    private val prefs: Prefs by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.feature_cards, container, false).apply { initView() }

    override fun invalidate() = withState(viewModel) { state ->
        cardsAdapter.submitList(state.cards)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.cards_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.cardAdd -> {
            AddCardView.createDialog(requireContext(), viewModel::addCard).show()
            true
        }
        R.id.log -> {
            AlertDialog.Builder(requireContext())
                    .setMessage(prefs.getLog())
                    .show()
            true
        }
        R.id.version -> {
            Toast.makeText(view!!.context, BuildConfig.VERSION_CODE.toString(), Toast.LENGTH_SHORT).show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onChildViewDetachedFromWindow(view: View) {
        if (view is CardItemView) {
            view.likesClickedListener = null
            view.dislikesClickedListener = null
        }
    }

    override fun onChildViewAttachedToWindow(view: View) {
        if (view is CardItemView) {
            view.likesClickedListener = {
                viewModel.like(it.id, !it.hasLiked)
            }
            view.dislikesClickedListener = {
                viewModel.dislike(it.id, !it.hasDisliked)
            }
        }
    }

    private fun View.initView() {
        cardsRv.adapter = cardsAdapter
        cardsRv.addOnChildAttachStateChangeListener(this@CardFragment)
        cardsRv.disableChangeAnimations()

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, START or END) {
            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                viewHolder.adapterPosition.let {
                    if (it != NO_POSITION) {
                        viewModel.removeCard(cardsAdapter.getCard(it).id)
                    }
                }
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean = false
        }).attachToRecyclerView(cardsRv)
    }

}