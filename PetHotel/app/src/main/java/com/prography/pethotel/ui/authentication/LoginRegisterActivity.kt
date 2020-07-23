package com.prography.pethotel.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.prography.pethotel.R
import com.prography.pethotel.room.AppDatabase
import com.prography.pethotel.room.auth.AccountPropViewModelFactory
import com.prography.pethotel.room.auth.AccountPropertiesViewModel
import com.prography.pethotel.ui.authentication.register.RegisterViewModel
import com.prography.pethotel.ui.authentication.login.LoginViewModel
import com.prography.pethotel.ui.authentication.login.LoginViewModelFactory
import com.prography.pethotel.utils.AuthTokenViewModel
import com.prography.pethotel.utils.AuthTokenViewModelFactory

@Suppress("DEPRECATION")
class LoginRegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var authTokenViewModel: AuthTokenViewModel

    private lateinit var accountPropertiesViewModel: AccountPropertiesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        // TODO network checking! Base Activity 만들어서 네트워크 확인하기.

        authTokenViewModel = ViewModelProvider(this, AuthTokenViewModelFactory())
                                .get(AuthTokenViewModel::class.java)

        registerViewModel = ViewModelProviders.of(this)[RegisterViewModel::class.java]

        loginViewModel = ViewModelProviders.of(this,
            LoginViewModelFactory()
        )[LoginViewModel::class.java]


        val database = AppDatabase.getInstance(this)
        val accountDao = database.accountPropertiesDao

        accountPropertiesViewModel = ViewModelProvider(
            this,
            AccountPropViewModelFactory(accountDao, application)
        )[AccountPropertiesViewModel::class.java]

        authTokenViewModel = ViewModelProvider(this, AuthTokenViewModelFactory())
            .get(AuthTokenViewModel::class.java)
    }
}
