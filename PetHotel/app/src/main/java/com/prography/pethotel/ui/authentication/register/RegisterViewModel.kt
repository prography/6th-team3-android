package com.prography.pethotel.ui.authentication.register

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prography.pethotel.api.auth.request.CheckPetBody
import com.prography.pethotel.api.auth.response.CheckPetResponseData
import com.prography.pethotel.api.auth.response.PetNumResponse
import com.prography.pethotel.api.auth.response.PostPetResponse
import com.prography.pethotel.api.auth.response.UserToken
import com.prography.pethotel.api.main.response.UserInfoResponse
import com.prography.pethotel.models.GeneralUserInfo
import com.prography.pethotel.models.PetInfo
import com.prography.pethotel.ui.authentication.kakao.RegisterFormState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import okhttp3.MultipartBody
import okhttp3.RequestBody


private const val TAG = "RegisterViewModel"
class RegisterViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope  = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _userInfo : MutableLiveData<GeneralUserInfo> = MutableLiveData()
    val userInfo : LiveData<GeneralUserInfo>
        get() = _userInfo


    /* Edit Text 필드의 상태 */
    private val _registrationForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registrationForm


    fun setUserInfo(generalUserInfo: GeneralUserInfo){
        _userInfo.value = generalUserInfo
        Log.d(TAG, "setUserInfo: $generalUserInfo")
    }

    fun setPetInfoToUser(pets : ArrayList<PetInfo>){
        _userInfo.value?.pets = pets
        Log.d(TAG, "setPetInfoToUser: $pets")
    }

    fun getUserInfoResponse() : LiveData<UserInfoResponse>{
        return RegisterRepository.userInfoResponse
    }

    fun getRegisterStatus(): LiveData<Boolean> {
        return RegisterRepository.registerStatus
    }

    fun getCheckPetResponse() : LiveData<CheckPetResponseData>{
        return RegisterRepository.checkPetResponse
    }

    fun getUserToken() : LiveData<UserToken>{
        return RegisterRepository.userToken
    }

    fun getPetNumberResponse() : LiveData<PetNumResponse>{
        return RegisterRepository.petInfo
    }

    fun getRegisterPetResponse() : LiveData<PostPetResponse>{
        return RegisterRepository.registerPetResponse
    }

    fun registerUserGeneral(generalUserInfo: GeneralUserInfo){
        RegisterRepository.generalRegister(generalUserInfo)
    }

    fun registerUserGeneralForm(
        registerUserInfo: HashMap<String, RequestBody>,
        photoUrl: MultipartBody.Part?
    ){
        RegisterRepository.generalRegisterForm(
            registerUserInfo,
            photoUrl
        )
    }

    fun checkPetNumber(checkPetBody: CheckPetBody){
        RegisterRepository.checkPet(checkPetBody)
    }

    fun registerPetToUser(token: String, registerPetParts: HashMap<String, RequestBody>, petProfileUrl : MultipartBody.Part?){
        //RegisterRepository.registerPetInfo(token, registerPetBody)
        RegisterRepository.registerPetForm(token, registerPetParts,  petProfileUrl)
    }

    /*이미 회원가입 된 사용자의 정보를 리턴한다.
    * 여기에 둘지, 아니면 다른 곳으로 뺄지 고민중 ... */
    fun getUser(userToken: String){
        RegisterRepository.getUser(userToken)
    }

    fun registerDataChanged(
        nickname : String? = null,
        email : String? = null,
        phone : String? = null,
        password : String? = null,
        passwordCheck : String? = null
    ){
        if(!isValidNickname(nickname)){
            _registrationForm.value = RegisterFormState(
                nicknameError = "닉네임은 한 글자 이상입니다."
            )
        }
        else if(!isEmailValid(email)){
            _registrationForm.value = RegisterFormState(
                emailError = "올바르지 않은 이메일 형식입니다."
            )
        }else if(!isPasswordCorrect(password, passwordCheck)){
            _registrationForm.value = RegisterFormState(
                passwordError = "비밀번호가 일치하지 않습니다."
            )
        }else if(!isPhoneNumberValid(phone)){
            _registrationForm.value = RegisterFormState(
                phoneNumberError = "휴대폰번호를 정확히 입력해 주세요."
            )
        }else{
            _registrationForm.value = RegisterFormState(
                isDataValid = true
            )
        }
    }

    private fun isValidNickname(nickname: String?): Boolean {
        if (nickname != null) {
            return nickname.isNotEmpty()
        }
        return false
    }

    private fun isPhoneNumberValid(phone: String?): Boolean {
        if (phone != null) {
            if(phone.contains('-')){
                return false
            }else if(phone.length > 11){
                return false
            }
        }
        return true
    }

    private fun isPasswordCorrect(password : String?, passwordCheck: String? = ""): Boolean {
        if(!password.isNullOrEmpty() &&
            !passwordCheck.isNullOrEmpty()){
            return password == passwordCheck
        }
        return false
    }

    private fun isEmailValid(email: String?): Boolean {
        if (email != null) {
            return if (email.contains('@')) {
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
            } else {
                email.isNotBlank()
            }
        }
        return true
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
