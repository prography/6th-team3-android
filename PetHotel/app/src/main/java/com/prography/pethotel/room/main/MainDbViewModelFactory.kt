package com.prography.pethotel.room.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalStateException

class MainDbViewModelFactory(
    val mainDao: MainDao,
    val application: Application
) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainDbViewModel::class.java)){
            return MainDbViewModel(
                mainDao,
                application
            ) as T
        }
        throw IllegalStateException("Unknown ViewModel")
    }
}