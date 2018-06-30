package com.satis.app.feature.cards.ui

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.satis.app.feature.cards.data.Card

class CardAdapter : ListAdapter<Card, CardAdapter.CardViewHolder>(object : DiffUtil.ItemCallback<Card>() {
    override fun areContentsTheSame(oldItem: Card, newItem: Card) = oldItem == newItem
    override fun areItemsTheSame(oldItem: Card, newItem: Card) = oldItem.id == newItem.id
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CardViewHolder(CardItemView(parent.context))

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.cardItemView.bind(getItem(position))
    }

    fun getCard(pos: Int) = getItem(pos)

    class CardViewHolder(val cardItemView: CardItemView) : RecyclerView.ViewHolder(cardItemView)

}