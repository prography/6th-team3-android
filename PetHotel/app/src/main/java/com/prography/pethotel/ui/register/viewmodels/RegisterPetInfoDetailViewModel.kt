package com.prography.pethotel.ui.register.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prography.pethotel.models.PetInfo

class RegisterPetInfoDetailViewModel : ViewModel() {

    val petInfoList : LiveData<ArrayList<PetInfo>> = MutableLiveData()

    //val petInfoLiveData : LiveData<PetInfo> = MutableLiveData()

}
