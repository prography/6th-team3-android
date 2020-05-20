package com.prography.pethotel.ui.register.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prography.pethotel.models.UserInfo

class RegisterViewModel : ViewModel() {

    val _userInfo : MutableLiveData<UserInfo> = MutableLiveData()

    
}
