package com.prography.pethotel.ui.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DateTimeVMFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(DateTimeViewModel::class.java).newInstance()
    }
}