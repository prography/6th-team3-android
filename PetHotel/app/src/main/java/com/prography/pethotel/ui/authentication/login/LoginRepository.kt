package com.prography.pethotel.ui.authentication.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.prography.pethotel.api.auth.PetmilyAuthApi
import com.prography.pethotel.api.auth.request.LoginInfoBody
import com.prography.pethotel.api.auth.response.GeneralLoginResponse
import com.prography.pethotel.api.auth.response.KakaoLoginResponse
import com.prography.pethotel.api.auth.response.UserToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import javax.security.auth.callback.Callback

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


    fun generalLogin(loginInfoBody: LoginInfoBody){
        val call
                = PetmilyAuthApi.authApiRetrofitService.generalLogin(loginInfoBody)
        val callback = object : retrofit2.Callback<GeneralLoginResponse>{
            override fun onFailure(call: Call<GeneralLoginResponse>, t: Throwable) {
                _loginResponse.value = null
            }

            override fun onResponse(
                call: Call<GeneralLoginResponse>,
                response: Response<GeneralLoginResponse>
            ) {
                _loginResponse.value = response.body()
            }
        }
        call.enqueue(callback)
    }

}