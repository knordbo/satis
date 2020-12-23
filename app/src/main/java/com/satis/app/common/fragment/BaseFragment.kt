package com.satis.app.common.fragment

import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView

open class BaseFragment: Fragment(), MavericksView {
  override fun invalidate() = Unit
}