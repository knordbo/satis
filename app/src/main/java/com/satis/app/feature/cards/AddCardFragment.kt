package com.satis.app.feature.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.airbnb.mvrx.targetFragmentViewModel
import com.airbnb.mvrx.withState
import com.satis.app.R
import com.satis.app.common.mvrx.BaseMvRxDialogFragment
import com.satis.app.utils.view.hideKeyboard
import com.satis.app.utils.view.showKeyboard
import kotlinx.android.synthetic.main.add_card.*

class AddCardFragment : BaseMvRxDialogFragment() {

    private val cardViewModel: CardViewModel by targetFragmentViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.add_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            title.requestFocus()
            requireContext().showKeyboard()
        }
        submit.setOnClickListener {
            cardViewModel.addCard()
        }
        title.doAfterTextChanged { editable ->
            cardViewModel.addCardTitleChanged(editable.toString())
        }
        message.doAfterTextChanged { editable ->
            cardViewModel.addCardMessageChanged(editable.toString())
        }

        cardViewModel.asyncSubscribe(viewLifecycleOwner, CardState::creatingCardAsync,
                onSuccess = {
                    requireContext().hideKeyboard(title)
                    dismiss()
                },
                onFail = {
                    Toast.makeText(context, R.string.card_message_no_title, Toast.LENGTH_SHORT).show()
                })
    }

    override fun invalidate() = withState(cardViewModel) { cardState ->
        if (title.text.toString() != cardState.creatingCard.title) {
            title.setText(cardState.creatingCard.title)
        }
        if (message.text.toString() != cardState.creatingCard.message) {
            message.setText(cardState.creatingCard.message)
        }
    }
}