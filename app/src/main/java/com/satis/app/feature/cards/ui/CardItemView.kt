package com.satis.app.feature.cards.ui

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.setPadding
import com.satis.app.R
import com.satis.app.feature.cards.data.Card
import com.satis.app.utils.view.layoutInflater
import kotlinx.android.synthetic.main.card_item.view.*

class CardItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var likesClickedListener: ((Card) -> Unit)? = null
    var dislikesClickedListener: ((Card) -> Unit)? = null

    init {
        layoutInflater.inflate(R.layout.card_item, this, true)
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        setPadding(context.resources.getDimension(R.dimen.key_line_med).toInt())
    }

    fun bind(card: Card) {
        title.text = card.title
        message.text = card.message

        likes.text = card.likes.toString()
        dislikes.text = card.dislikes.toString()

        thumbUp.setImageDrawable(tint(thumbUp.drawable, ContextCompat.getColor(context, if (card.hasLiked) R.color.blue else R.color.gray)))
        thumbDown.setImageDrawable(tint(thumbDown.drawable, ContextCompat.getColor(context, if (card.hasDisliked) R.color.blue else R.color.gray)))

        thumbUp.setOnClickListener {
            likesClickedListener?.invoke(card)
        }
        thumbDown.setOnClickListener {
            dislikesClickedListener?.invoke(card)
        }
    }

    private fun tint(drawable: Drawable, color: Int): Drawable {
        val mutated = drawable.mutate()
        DrawableCompat.setTint(mutated, color)
        DrawableCompat.setTintMode(mutated, PorterDuff.Mode.SRC_IN)
        return mutated
    }

}