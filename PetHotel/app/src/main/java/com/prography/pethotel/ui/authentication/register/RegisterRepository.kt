package com.prography.pethotel.ui.authentication.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kakao.usermgmt.response.model.User
import com.prography.pethotel.api.auth.PetmilyAuthApi
import com.prography.pethotel.api.auth.request.*
import com.prography.pethotel.api.auth.response.*
import com.prography.pethotel.api.main.response.UserInfoResponse
import com.prography.pethotel.models.GeneralUserInfo
import com.prography.pethotel.models.PetInfo
import com.prography.pethotel.utils.SERVICE_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

private const val TAG = "RegisterRepository"

object RegisterRepository{

    private val coroutineScope  = CoroutineScope(Dispatchers.Main)

    /*동물등록번호 API 호출 시 오는 응답에 대한 라이브 데이터*/
    private val _petInfo : MutableLiveData<PetNumResponse> = MutableLiveData()
    val petInfo : LiveData<PetNumResponse>
        get() = _petInfo

    /* 회원가입시 발급되는 유저 토큰 */
    private val _userToken : MutableLiveData<UserToken> = MutableLiveData()
    val userToken : LiveData<UserToken>
        get() = _userToken

    /*회원가입 성공 혹은 실패에 대한 정보 */
    private val _registerStatus : MutableLiveData<Boolean> = MutableLiveData()
    val registerStatus : LiveData<Boolean>
        get() = _registerStatus

    private val _kakaoRegisterResponse : MutableLiveData<RegistrationResponse> = MutableLiveData()
    val kakaoRegisterResponse : LiveData<RegistrationResponse>
        get() = _kakaoRegisterResponse

    /*유저 토큰으로 요청해서 받아오는 유저 정보*/
    private val _userInfoRespone : MutableLiveData<UserInfoResponse> = MutableLiveData()
    val userInfoResponse : LiveData<UserInfoResponse>
        get() = _userInfoRespone

    /* 펫 POST 할 때의 응답 */
    private val _registerPetResponse : MutableLiveData<PostPetResponse> = MutableLiveData()
    val registerPetResponse : LiveData<PostPetResponse>
        get() = _registerPetResponse

    private val _checkPetResponse : MutableLiveData<CheckPetResponseData> = MutableLiveData()
    val checkPetResponse : LiveData<CheckPetResponseData>
        get() = _checkPetResponse


    fun checkPet(checkPetBody: CheckPetBody){
        val call = PetmilyAuthApi.authApiRetrofitService.checkPet(checkPetBody)
        val callback = object : Callback<CheckPetResponse>{
            override fun onFailure(call: Call<CheckPetResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                _checkPetResponse.value = null
            }

            override fun onResponse(
                call: Call<CheckPetResponse>,
                response: Response<CheckPetResponse>
            ) {
                Log.d(TAG, "onResponse: ${response.body()}")
                if(response.body() != null) {
                    if(response.body()!!.status == "success"){
                        _checkPetResponse.value = response.body()!!.data
                    }else{
                        _checkPetResponse.value = null
                    }
                }
            }
        }
        call.enqueue(callback)
    }

    fun registerPetInfo(token : String, registerPetBody: RegisterPetBody){

        val call = PetmilyAuthApi.authApiRetrofitService.registerPet(
            token = token,
            registerPetBody = registerPetBody
        )
        val callback = object :Callback<PostPetResponse>{
            override fun onFailure(call: Call<PostPetResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

            override fun onResponse(
                call: Call<PostPetResponse>,
                response: Response<PostPetResponse>
            ) {
                Log.d(TAG, "onResponse: ${response.body()}")
            }
        }
        call.enqueue(callback)
    }


    /*일반 회원가입. 유저의 이름, 이메일, 비밀번호, 전화번호를 서버로 전송한다. 응답은 토큰이고,
    * 이 토큰을 저장해 두었다가 다른 요청때 헤더로 넣어서 전송한다. */
    fun generalRegister(generalUserInfo: GeneralUserInfo){
        val registerInfo =
            RegisterUserInfo(
                UserData(
                    generalUserInfo.nickName!!,
                    generalUserInfo.phoneNumber,
                    generalUserInfo.email!!,
                    generalUserInfo.password!!
                )
            )
        Log.d(TAG, "generalRegister: ${generalUserInfo}")

        val registerCall = PetmilyAuthApi.authApiRetrofitService.generalRegister(registerInfo)

        val callback = object : Callback<RegistrationResponse>{
            override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                call.cancel()
                _registerStatus.value = false
                _userToken.value = UserToken("")
            }

            override fun onResponse(
                call: Call<RegistrationResponse>,
                response: Response<RegistrationResponse>
            ) {
                Log.d(TAG, "onResponse: ${response.body()}")
                if(response.body() == null || !response.isSuccessful){
                    _registerStatus.value = false
                    _userToken.value = UserToken("")
                }else{
                    _registerStatus.value = response.body()!!.status == "success"
                    _userToken.value = response.body()!!.userToken
                }
            }
        }
        registerCall.enqueue(callback)
    }

    /*카카오 회원가입. 유저 아이디와 유저 이메일, 전화번호, 프로필 사진을 서버로 전송한다. */
    fun kakaoRegister(kakaoRegisterBody: KakaoRegisterBody){
        try {
            coroutineScope.launch {
                val response =
                    PetmilyAuthApi.authApiRetrofitService.kakaoRegister(
                        kakaoRegisterBody
                    )
                Log.d(TAG, "kakaoRegister: $response")
                /*카카오 회원가입 또한 동일한 register status, user token 객체에
                * 데이터를 setting 한다 */
                _kakaoRegisterResponse.value = response
//                _registerStatus.value = response.status == "success"
//                _userToken.value = response.userToken
            }
        }catch (e : Exception){
            Log.d(TAG, "kakaoRegister: ${e.printStackTrace()}")
        }
    }


    fun getUser(userToken: String){
        val getUserCall = PetmilyAuthApi.authApiRetrofitService.getUser(userToken)
        val callback = object : Callback<UserInfoResponse>{
            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                Log.d(TAG, "onFailure: ${t.printStackTrace()}")
                _userInfoRespone.value = null
            }

            override fun onResponse(
                call: Call<UserInfoResponse>,
                response: Response<UserInfoResponse>
            ) {
                _userInfoRespone.value = response.body()
            }
        }
        getUserCall.enqueue(callback)
    }
}