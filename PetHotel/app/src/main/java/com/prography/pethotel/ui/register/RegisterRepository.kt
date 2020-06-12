package com.prography.pethotel.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.prography.pethotel.api.main.MainApi
import com.prography.pethotel.api.main.response.AnimalCheckResponse
import com.prography.pethotel.models.Hotel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class RegisterRepository(
    val mainApi : MainApi
){

    companion object{
        //TODO make this singleton
    }

    private val viewModelJob = Job()
    private val uiScope  = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _petInfo : MutableLiveData<AnimalCheckResponse> = MutableLiveData()
    val petInfo : LiveData<AnimalCheckResponse>
        get() = _petInfo

    private suspend fun checkPublicApiForPetInfo(dogRegNo : String, serviceKey : String){
        try {
            uiScope.launch {
                val response = mainApi.publicApiRetrofitService.getAnimalCheckResponse(dog_reg_no = dogRegNo)
                _petInfo.value = response
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }


}