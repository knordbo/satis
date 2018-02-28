package com.satis.app.feature.colors.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.satis.app.feature.colors.persistence.ColorDao

class ColorViewModelFactory(private val colorDao: ColorDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ColorViewModel::class.java)) {
            return ColorViewModel(colorDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}