package com.satis.app.common.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.airbnb.mvrx.MavericksView

abstract class BaseFragment<Binding : ViewBinding> : Fragment(), MavericksView {

    private var _binding: Binding? = null

    val binding: Binding get() = _binding!!

    abstract val bind: (LayoutInflater, ViewGroup?, Boolean) -> Binding?

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bind(inflater, container, false)
        return _binding?.root ?: super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun invalidate() {
        // Optional
    }

}