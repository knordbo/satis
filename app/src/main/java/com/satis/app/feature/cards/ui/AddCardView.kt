package com.satis.app.feature.cards.ui

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AlertDialog
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.view.setPadding
import com.satis.app.R
import com.satis.app.feature.cards.data.Card
import com.satis.app.utils.view.hideKeyboard
import com.satis.app.utils.view.layoutInflater
import com.satis.app.utils.view.showKeyboard
import kotlinx.android.synthetic.main.add_card.view.*

class AddCardView(context: Context) : ConstraintLayout(context) {

    var addListener: ((Card) -> Unit)? = null

    init {
        layoutInflater.inflate(R.layout.add_card, this, true)
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        setPadding(resources.getDimension(R.dimen.key_line).toInt())
        submit.setOnClickListener {
            addListener?.invoke(Card(title = title.text.toString(), message = message.text.toString()))
        }
        context.showKeyboard()
    }

    companion object {
        fun createDialog(context: Context, addListener: ((Card) -> Unit)): AlertDialog {
            val view = AddCardView(context)
            val dialog = AlertDialog.Builder(context)
                    .setView(view)
                    .setOnDismissListener {
                        context.hideKeyboard(view)
                    }
                    .create()
            val extendedListener: ((Card) -> Unit) = {
                addListener.invoke(it)
                dialog.dismiss()
            }
            view.addListener = extendedListener
            return dialog
        }
    }

}