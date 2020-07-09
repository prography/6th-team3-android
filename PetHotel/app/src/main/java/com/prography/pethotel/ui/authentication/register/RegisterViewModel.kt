package com.prography.pethotel.ui.authentication.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prography.pethotel.api.auth.request.KakaoRegisterBody
import com.prography.pethotel.api.auth.response.UserToken
import com.prography.pethotel.api.main.response.PetNumberResponse
import com.prography.pethotel.models.GeneralUserInfo
import com.prography.pethotel.models.PetInfo
import com.prography.pethotel.utils.SERVICE_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception


private const val TAG = "RegisterViewModel"
class RegisterViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope  = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _userInfo : MutableLiveData<GeneralUserInfo> = MutableLiveData()
    val userInfo : LiveData<GeneralUserInfo>
        get() = _userInfo

    fun setUserInfo(generalUserInfo: GeneralUserInfo){
        _userInfo.value = generalUserInfo
        Log.d(TAG, "setUserInfo: $generalUserInfo")
    }

    fun setPetInfoToUser(pets : ArrayList<PetInfo>){
        _userInfo.value?.pets = pets
        Log.d(TAG, "setPetInfoToUser: $pets")
    }

    fun getRegisterStatus(): LiveData<Boolean> {
        return RegisterRepository.registerStatus
    }

    fun getUserToken() : LiveData<UserToken>{
        return RegisterRepository.userToken
    }

    fun getPetNumberResponse() : LiveData<PetNumberResponse>{
        return RegisterRepository.petInfo
    }

    fun registerUserGeneral(generalUserInfo: GeneralUserInfo){
        RegisterRepository.generalRegister(generalUserInfo)
    }

    fun registerUserKakao(kakaoRegisterBody: KakaoRegisterBody){
        RegisterRepository.kakaoRegister(kakaoRegisterBody)
    }

    fun checkPetNumber(dogRegNo : String, serviceKey : String = SERVICE_KEY){
        RegisterRepository.checkPublicApiForPetInfo(dogRegNo, serviceKey)
    }

    /*이미 회원가입 된 사용자의 정보를 리턴한다.
    * 여기에 둘지, 아니면 다른 곳으로 뺄지 고민중 ... */
    fun getUser(userToken: UserToken){
        RegisterRepository.getUser(userToken)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
