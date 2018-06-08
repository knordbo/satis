package com.satis.app.feature.cards.ui

import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.satis.app.feature.cards.data.Card

class CardAdapter : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Card>() {
        override fun areContentsTheSame(oldItem: Card, newItem: Card) = oldItem == newItem
        override fun areItemsTheSame(oldItem: Card, newItem: Card) = oldItem.id == newItem.id
    })

    fun bind(cards: List<Card>) {
        differ.submitList(cards)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CardViewHolder(CardItemView(parent.context))

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.cardItemView.bind(differ.currentList[position])
    }

    class CardViewHolder(val cardItemView: CardItemView) : RecyclerView.ViewHolder(cardItemView)

}