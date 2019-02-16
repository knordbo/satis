package com.satis.app

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment

// TODO remove when FragmentNavigator delegates to FragmentFactory
class FragmentFactorySupportedNavHostFragment : NavHostFragment() {
    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination> =
            FragmentFactorySupportedFragmentNavigator(requireContext(), childFragmentManager, id)
}

@Navigator.Name("fragment")
private class FragmentFactorySupportedFragmentNavigator(
        context: Context,
        manager: FragmentManager,
        containerId: Int
) : FragmentNavigator(
        context,
        manager,
        containerId
) {
    override fun instantiateFragment(context: Context, fragmentManager: FragmentManager, className: String, args: Bundle?): Fragment =
            fragmentManager.fragmentFactory.instantiate(context.classLoader, className, args)
}