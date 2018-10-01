package com.satis.app.feature.cards.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.satis.app.feature.cards.data.Card
import com.satis.app.feature.cards.ui.CardAdapter.CardViewHolder

class CardAdapter : ListAdapter<Card, CardViewHolder>(object : ItemCallback<Card>() {
    override fun areContentsTheSame(oldItem: Card, newItem: Card) = oldItem == newItem
    override fun areItemsTheSame(oldItem: Card, newItem: Card) = oldItem.id == newItem.id
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CardViewHolder(CardItemView(parent.context))

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.cardItemView.bind(getItem(position))
    }

    fun getCard(pos: Int) = getItem(pos)

    class CardViewHolder(val cardItemView: CardItemView) : ViewHolder(cardItemView)

}