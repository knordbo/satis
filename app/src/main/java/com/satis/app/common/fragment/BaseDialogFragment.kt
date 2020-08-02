package com.satis.app.common.fragment

import androidx.appcompat.app.AppCompatDialogFragment
import com.airbnb.mvrx.MavericksView

abstract class BaseDialogFragment : AppCompatDialogFragment(), MavericksView {
  override fun invalidate() {
    // Optional
  }
}