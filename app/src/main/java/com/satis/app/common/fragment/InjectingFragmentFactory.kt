package com.satis.app.common.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject
import javax.inject.Provider

class InjectingFragmentFactory @Inject constructor(
  private val creators: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>
) : FragmentFactory() {

  override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
    val creator = creators[loadFragmentClass(classLoader, className)]

    return if (creator != null) {
      creator.get()
    } else {
      super.instantiate(classLoader, className)
    }
  }

}
