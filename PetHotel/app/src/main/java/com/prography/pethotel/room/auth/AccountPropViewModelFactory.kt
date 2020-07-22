package com.prography.pethotel.room.auth

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalStateException


class AccountPropViewModelFactory(
    private val accountPropertiesDao: AccountPropertiesDao,
    private val application: Application
) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AccountPropertiesViewModel::class.java)){
            return AccountPropertiesViewModel(
                accountPropertiesDao,
                application) as T
        }
        throw IllegalStateException("Unknown ViewModel Class")
    }
}
