package com.prography.pethotel.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.prography.pethotel.R
import com.prography.pethotel.ui.authentication.register.RegisterViewModel
import com.prography.pethotel.ui.authentication.login.LoginViewModel
import com.prography.pethotel.ui.authentication.login.LoginViewModelFactory
import com.prography.pethotel.utils.LoginStateViewModel
import com.prography.pethotel.utils.LoginStateViewModelFactory

class LoginRegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var loginStateViewModel: LoginStateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        // TODO network checking! Base Activity 만들어서 네트워크 확인하기.

        loginStateViewModel = ViewModelProvider(this, LoginStateViewModelFactory())
                                .get(LoginStateViewModel::class.java)

        registerViewModel = ViewModelProviders.of(this)[RegisterViewModel::class.java]

        loginViewModel = ViewModelProviders.of(this,
            LoginViewModelFactory()
        )[LoginViewModel::class.java]

    }
}
