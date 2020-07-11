package com.prography.pethotel.ui.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.prography.pethotel.R
import com.prography.pethotel.ui.MainActivity
import com.prography.pethotel.ui.authentication.LoginRegisterActivity
import com.prography.pethotel.utils.USER_TOKEN
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIMEOUT : Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //hiding title bar of this activity
        window.requestFeature(Window.FEATURE_NO_TITLE)
        //making this activity full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_splash)
        val translate = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        splash_app_description.startAnimation(translate)

        val pref = getSharedPreferences(USER_TOKEN, Context.MODE_PRIVATE)
        val token = pref.getString(USER_TOKEN, "")

        /* 왜 if else 순서를 바꾸면 MainActivity 가 두 번 생성될까 .... ? */
        if(!token.isNullOrEmpty()){
            Handler().postDelayed({
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }, SPLASH_TIMEOUT)
        }else{
            Handler().postDelayed({
                startActivity(Intent(this@SplashActivity, LoginRegisterActivity::class.java))
                finish()
            }, SPLASH_TIMEOUT)
        }
        /* 유저 토큰이 있는지 확인하고, 없으면 로그인으로 있으면 메인으로 넘어간다. */
    }
}
