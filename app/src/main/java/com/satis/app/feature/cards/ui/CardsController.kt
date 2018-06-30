package com.satis.app.feature.cards.ui

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.satis.app.BuildConfig
import com.satis.app.R
import com.satis.app.conductor.BaseController
import com.satis.app.feature.cards.redux.CardDispatcherViewHolder
import com.satis.app.feature.cards.redux.CardViewState
import com.satis.app.redux.DispatcherBinder
import kotlinx.android.synthetic.main.feature_cards.view.*

class CardsController : BaseController(), RecyclerView.OnChildAttachStateChangeListener {

    private val cardDispatcher by lazy { viewModelProvider().get(CardDispatcherViewHolder::class.java).dispatcher }
    private val cardDispatcherBinder by lazy { DispatcherBinder(lifecycle, cardDispatcher, this::render) }
    private val cardsAdapter = CardAdapter()

    init {
        setHasOptionsMenu(true)
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View =
            inflater.inflate(R.layout.feature_cards, container, false)

    override fun onViewInflated(view: View) {
        super.onViewInflated(view)
        cardDispatcherBinder // invoke to ensure initialised
        with(view) {
            cardsRv.adapter = cardsAdapter
            cardsRv.addOnChildAttachStateChangeListener(this@CardsController)
            (cardsRv.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        }
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
                cardDispatcher.like(it.id, !it.hasLiked)
            }
            view.dislikesClickedListener = {
                cardDispatcher.dislike(it.id, !it.hasDisliked)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.cards_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.cardAdd -> {
            AddCardView.createDialog(view!!.context, cardDispatcher::addCard).show()
            true
        }
        R.id.version -> {
            Toast.makeText(view!!.context, BuildConfig.VERSION_CODE.toString(), Toast.LENGTH_SHORT).show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun render(cardViewState: CardViewState) {
        cardsAdapter.submitList(cardViewState.cards)
    }

}