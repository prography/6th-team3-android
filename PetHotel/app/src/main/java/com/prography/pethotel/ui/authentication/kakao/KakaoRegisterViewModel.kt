package com.prography.pethotel.ui.authentication.kakao

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prography.pethotel.api.auth.request.KakaoRegisterBody
import com.prography.pethotel.api.auth.response.KakaoRegistrationResponse
import com.prography.pethotel.api.auth.response.RegistrationResponse
import com.prography.pethotel.ui.authentication.register.RegisterRepository
import okhttp3.RequestBody

class KakaoRegisterViewModel : ViewModel(){

    /* 카카오 서버로부터 받은 유저 정보들 */
    private val _kakaoRegisterUserData : MutableLiveData<KakaoRegistrationResponse> = MutableLiveData()
    val kakaoRegisterUserData : LiveData<KakaoRegistrationResponse>
        get() = _kakaoRegisterUserData

    /* Edit Text 필드의 상태 */
    private val _registrationForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registrationForm


    fun setKakaoRegisterData(kakaoRegistrationResponse: KakaoRegistrationResponse){
        _kakaoRegisterUserData.value = kakaoRegistrationResponse
    }

//    fun registerUserKakao(kakaoRegisterBody: KakaoRegisterBody){
//        RegisterRepository.kakaoRegister(kakaoRegisterBody)
//    }

    fun registerUserKakaoForm(kakaoRegisterData : HashMap<String, RequestBody?>){
        RegisterRepository.kakaoRegisterForm(kakaoRegisterData)
    }

    fun getRegisterStatus() : LiveData<RegistrationResponse>{
        return RegisterRepository.kakaoRegisterResponse
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
}