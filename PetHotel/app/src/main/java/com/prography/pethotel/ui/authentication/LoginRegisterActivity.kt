package com.prography.pethotel.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.prography.pethotel.R
import com.prography.pethotel.ui.authentication.register.RegisterViewModel
import com.prography.pethotel.ui.authentication.login.LoginViewModel
import com.prography.pethotel.ui.authentication.login.LoginViewModelFactory

class LoginRegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        // TODO network checking! Base Activity 만들어서 네트워크 확인하기.

        registerViewModel = ViewModelProviders.of(this)[RegisterViewModel::class.java]

        loginViewModel = ViewModelProviders.of(this,
            LoginViewModelFactory()
        )[LoginViewModel::class.java]

    }
}
