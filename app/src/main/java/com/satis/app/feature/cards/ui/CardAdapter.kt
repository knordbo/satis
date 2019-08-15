package com.satis.app.feature.cards.ui

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.satis.app.R
import com.satis.app.feature.cards.data.Card
import com.satis.app.feature.cards.ui.CardAdapter.CardViewHolder
import com.satis.app.utils.view.asyncText
import com.satis.app.utils.view.layoutInflater
import kotlinx.android.synthetic.main.card_item.view.*

class CardAdapter(
        private val onLikeClicked: ((Card) -> Unit),
        private val onDislikeClicked: ((Card) -> Unit),
        private val onSwiped: ((Card) -> Unit)
) : ListAdapter<Card, CardViewHolder>(Differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
                view = parent.layoutInflater.inflate(R.layout.card_item, parent, false),
                onLikeClicked = onLikeClicked,
                onDislikeClicked = onDislikeClicked
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun onSwiped(adapterPosition: Int) {
        onSwiped(getItem(adapterPosition))
    }

    class CardViewHolder(
            private val view: View,
            private val onLikeClicked: ((Card) -> Unit),
            private val onDislikeClicked: ((Card) -> Unit)
    ) : ViewHolder(view) {

        fun bind(card: Card) = with(view) {
            title.asyncText = card.title
            message.asyncText = card.message

            likes.asyncText = card.likes.toString()
            dislikes.asyncText = card.dislikes.toString()

            thumbUp.setImageDrawable(tint(thumbUp.drawable, ContextCompat.getColor(context, if (card.hasLiked) R.color.blue else R.color.gray)))
            thumbDown.setImageDrawable(tint(thumbDown.drawable, ContextCompat.getColor(context, if (card.hasDisliked) R.color.blue else R.color.gray)))

            thumbUp.setOnClickListener {
                onLikeClicked.invoke(card)
            }
            thumbDown.setOnClickListener {
                onDislikeClicked.invoke(card)
            }
        }

        private fun tint(drawable: Drawable, color: Int): Drawable {
            val mutated = drawable.mutate()
            DrawableCompat.setTint(mutated, color)
            DrawableCompat.setTintMode(mutated, PorterDuff.Mode.SRC_IN)
            return mutated
        }

    }

    private object Differ : ItemCallback<Card>() {
        override fun areContentsTheSame(oldItem: Card, newItem: Card) = oldItem == newItem
        override fun areItemsTheSame(oldItem: Card, newItem: Card) = oldItem.id == newItem.id
    }

}