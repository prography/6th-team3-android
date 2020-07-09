package com.prography.pethotel.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoginStateViewModelFactory : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginStateViewModel::class.java)) {
            return LoginStateViewModel(
                application = Application()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}