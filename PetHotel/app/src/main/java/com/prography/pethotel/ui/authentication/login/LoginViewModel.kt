package com.prography.pethotel.ui.authentication.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.prography.pethotel.R
import com.prography.pethotel.api.auth.request.LoginInfoBody
import com.prography.pethotel.api.auth.response.GeneralLoginResponse
import com.prography.pethotel.api.auth.response.UserToken


private const val TAG = "LoginViewModel"

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun getLoginResponse() : LiveData<GeneralLoginResponse>{
        return loginRepository.loginResponse
    }

    fun login(loginInfoBody : LoginInfoBody) {
        // can be launched in a separate asynchronous job
        Log.d(TAG, "login: 뷰모델에서 로그인 중")
        loginRepository.generalLogin(loginInfoBody)

//        if (result is Result.Success) {
//            _loginResult.value =
//                LoginResult(
//                    success = UserToken(result.data.data.token)
//                )
//        } else {
//            _loginResult.value =
//                LoginResult(error = R.string.login_failed)
//        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value =
                LoginFormState(
                    usernameError = R.string.invalid_username
                )
        } else if (!isPasswordValid(password)) {
            _loginForm.value =
                LoginFormState(
                    passwordError = R.string.invalid_password
                )
        } else {
            _loginForm.value =
                LoginFormState(
                    isDataValid = true
                )
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}