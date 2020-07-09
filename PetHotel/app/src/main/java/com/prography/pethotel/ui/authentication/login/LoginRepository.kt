package com.prography.pethotel.ui.authentication.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.prography.pethotel.api.auth.PetmilyAuthApi
import com.prography.pethotel.api.auth.request.LoginInfoBody
import com.prography.pethotel.api.auth.response.GeneralLoginResponse
import com.prography.pethotel.api.auth.response.UserToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.lang.Exception

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

private const val TAG = "LoginRepository"

class LoginRepository() {


    private val coroutineScope  = CoroutineScope(Dispatchers.Main)

    // in-memory cache of the loggedInUser object
    var user: LoginInfoBody? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    private val _loginResponse : MutableLiveData<GeneralLoginResponse> = MutableLiveData()
    val loginResponse : LiveData<GeneralLoginResponse>
        get() = _loginResponse


    fun logout() {
        user = null
        //TODO logout 하기
    }


//    fun login(loginInfoBody: LoginInfoBody): Result<GeneralLoginResponse> {
//        // handle login
//        Log.d(TAG, "login: 레포에서 로그인 중")
//        val result = generalLogin(loginInfoBody = loginInfoBody)
//        if (result is Result.Success) {
//            val userToken = result.data.data.token
//            setLoggedInUser(userToken)
//        }else{
//            Log.d(TAG, "login: 로그인 실패")
//        }
//        return result
//    }

    private fun setLoggedInUser(userToken: String) {
        Log.d(TAG, "setLoggedInUser: $userToken Login 성공!")
        //this.user = loginInfoBody
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    suspend fun kakaoLogin(){
        try {
            coroutineScope.launch {
                val response =
                    PetmilyAuthApi.authApiRetrofitService.kakaoLogin()
                Log.d(TAG, "kakaoRegister: $response")
            }
        }catch (e : Exception){
            Log.d(TAG, "kakaoRegister: ${e.printStackTrace()}")
        }
    }

    fun generalLogin(loginInfoBody: LoginInfoBody){
        var response : GeneralLoginResponse? = null

        try {
            val job = coroutineScope.launch {
                response =
                    PetmilyAuthApi.authApiRetrofitService.generalLogin(
                        loginInfoBody
                    )
                _loginResponse.value = response
            }
        }catch (e : Exception){
            Log.d(TAG, "generalLogin: ${e.printStackTrace()}")
        }
    }

}