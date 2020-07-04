package com.prography.pethotel.ui.register

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.MediaSession2
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException

import com.prography.pethotel.R
import com.prography.pethotel.ui.MainActivity
import com.prography.pethotel.ui.register.viewmodels.LoginRegisterViewModel
import kotlinx.android.synthetic.main.login_register_fragment.*

class LoginRegisterFragment : Fragment() {

    companion object {
        fun newInstance() =
            LoginRegisterFragment()

        private const val TAG = "LoginRegisterFragment"
    }

    private lateinit var viewModel: LoginRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Session.getCurrentSession().addCallback(sessionCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.login_register_fragment, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel::class.java)
        // TODO: Use the ViewModel

        //start front image animation
        front_img.setBackgroundResource(R.drawable.mily_animated)
        val animationDrawable =  front_img.background as AnimationDrawable
        animationDrawable.start()

        btn_login_user_login.setOnClickListener {
            //로그인 화면으로 이동한다.
            //TODO LOGIN 로그인 요청 날리기
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }

        btn_login_user_register.setOnClickListener {
            //회원가입 화면으로 이동한다.
            findNavController().navigate(R.id.action_loginRegisterFragment_to_registerUserInfoFragment)
        }

    }

    private val sessionCallback = object : ISessionCallback{
        override fun onSessionOpenFailed(exception: KakaoException?) {
            Log.d(Companion.TAG, "onSessionOpenFailed: Kakao Login Failed!\n$exception")
        }

        override fun onSessionOpened() {
            Log.d(Companion.TAG, "onSessionOpened: Kakko Login Success!")
            //로그인 성공시 액세스 토큰, 리프레시 토큰 두 종류의 사용자 토큰을 받는다.
            //카카오 SDK가 토큰을 관리하는 기능을 갖고 있음.
            //Android 카카오 SDK는 토큰 관리를 위한 별도 설정이 필요하지 않아.
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        //세션 콜백을 삭제한다.
        Session.getCurrentSession().removeCallback(sessionCallback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //카카오톡 간편로그인 실행 결과를 받아서 SDK로 전달한다.
        if(Session.getCurrentSession().handleActivityResult(
                requestCode, resultCode, data
            )){
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}
