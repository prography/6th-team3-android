package com.prography.pethotel.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AuthTokenViewModelFactory : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthTokenViewModel::class.java)) {
            return AuthTokenViewModel(
                application = Application()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}