package com.satis.app.feature.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.satis.app.R
import com.satis.app.databinding.AddCardBinding
import com.satis.app.utils.view.hideKeyboard
import com.satis.app.utils.view.showKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCardFragment : AppCompatDialogFragment() {

  private val cardViewModel: CardViewModel by viewModels({ targetFragment!! })

  private lateinit var binding: AddCardBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
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


    viewLifecycleOwner.lifecycleScope.launchWhenCreated {
      cardViewModel.state.collect { state ->
        if (binding.title.text.toString() != state.creatingCard.title) {
          binding.title.setText(state.creatingCard.title)
        }
        if (binding.message.text.toString() != state.creatingCard.message) {
          binding.message.setText(state.creatingCard.message)
        }

        when (state.creatingCardEvent) {
          CreatingCardEvent.NoTitle -> {
            Toast.makeText(context, R.string.card_message_no_title, Toast.LENGTH_SHORT).show()
          }
          CreatingCardEvent.Success -> {
            requireContext().hideKeyboard(binding.title)
            dismiss()
          }
          CreatingCardEvent.None -> Unit
        }
        if (state.creatingCardEvent != CreatingCardEvent.None) {
          cardViewModel.addCardEventHandled()
        }
      }
    }
  }
}