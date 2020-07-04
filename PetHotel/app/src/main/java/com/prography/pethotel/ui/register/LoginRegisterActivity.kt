package com.prography.pethotel.ui.register

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kakao.auth.IApplicationConfig
import com.kakao.auth.KakaoAdapter
import com.kakao.auth.KakaoSDK
import com.prography.pethotel.R
import kotlinx.android.synthetic.main.input_field_layout_with_button.view.*
import kotlinx.android.synthetic.main.testing_input_field_layout.*

class LoginRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        // TODO network checking! Base Activity 만들어서 네트워크 확인하기.


        KakaoSDK.init(
            object : KakaoAdapter(){
                override fun getApplicationConfig(): IApplicationConfig {
                     return IApplicationConfig {
                         return@IApplicationConfig applicationContext
                     }
                }
            }
        )
    }

}
