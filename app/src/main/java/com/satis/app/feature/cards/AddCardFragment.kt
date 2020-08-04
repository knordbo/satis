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
import com.satis.app.common.fragment.BaseDialogFragment
import com.satis.app.databinding.AddCardBinding
import com.satis.app.utils.view.hideKeyboard
import com.satis.app.utils.view.showKeyboard

class AddCardFragment : BaseDialogFragment() {

  private val cardViewModel: CardViewModel by targetFragmentViewModel()

  private lateinit var binding: AddCardBinding

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = AddCardBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    if (savedInstanceState == null) {
      binding.title.requestFocus()
      requireContext().showKeyboard()
    }
    binding.submit.setOnClickListener {
      cardViewModel.addCard()
    }
    binding.title.doAfterTextChanged { editable ->
      cardViewModel.addCardTitleChanged(editable.toString())
    }
    binding.message.doAfterTextChanged { editable ->
      cardViewModel.addCardMessageChanged(editable.toString())
    }

    cardViewModel.onAsync(CardState::creatingCardAsync,
      onSuccess = {
        requireContext().hideKeyboard(binding.title)
        dismiss()
      },
      onFail = {
        Toast.makeText(context, R.string.card_message_no_title, Toast.LENGTH_SHORT).show()
      })
  }

  override fun invalidate() = withState(cardViewModel) { cardState ->
    if (binding.title.text.toString() != cardState.creatingCard.title) {
      binding.title.setText(cardState.creatingCard.title)
    }
    if (binding.message.text.toString() != cardState.creatingCard.message) {
      binding.message.setText(cardState.creatingCard.message)
    }
  }
}