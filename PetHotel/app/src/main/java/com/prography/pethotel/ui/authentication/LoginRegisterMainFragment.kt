package com.prography.pethotel.ui.authentication

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException

import com.prography.pethotel.R
import com.prography.pethotel.api.auth.request.LoginInfoBody
import com.prography.pethotel.api.auth.response.UserToken
import com.prography.pethotel.ui.MainActivity
import com.prography.pethotel.ui.authentication.register.RegisterViewModel
import com.prography.pethotel.ui.authentication.login.LoginViewModel
import com.prography.pethotel.ui.authentication.login.LoginViewModelFactory
import com.prography.pethotel.utils.USER_TOKEN
import kotlinx.android.synthetic.main.login_register_fragment.*

class LoginRegisterMainFragment : Fragment() {

    companion object {
        fun newInstance() =
            LoginRegisterMainFragment()

        private const val TAG = "LoginRegisterMainFragment"
    }

    private lateinit var viewModel: RegisterViewModel

    private lateinit var loginViewModel : LoginViewModel
    private lateinit var userToken : UserToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = requireActivity().getSharedPreferences(USER_TOKEN, MODE_PRIVATE)
        val token = pref.getString(USER_TOKEN, "")
        if(token == ""){

        }else{
            userToken = UserToken(token!!)
        }
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
        viewModel = ViewModelProvider(requireActivity()).get(RegisterViewModel::class.java)

        loginViewModel = ViewModelProvider(requireActivity(),
            LoginViewModelFactory()
        ).get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer

            /*인풋 필드가 완전히 입력되지 않으면 로그인 버튼이 눌리지 않는다.
            * 만약 이메일 / 비밀번호 형식이 틀렸으면 에러를 보여준다. */
            btn_login_user_login.isEnabled = loginState.isDataValid

            if(loginState.usernameError != null){
                et_login_username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                et_login_password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.getLoginResponse().observe(viewLifecycleOwner, Observer {
            val loginResult = it.status

            loading.visibility = View.GONE

            if (loginResult == "success") {
                updateUiWithUser(it.data.token)
            }
            else{
                showLoginFailed(R.string.login_failed)
            }
            requireActivity().setResult(Activity.RESULT_OK)
            //Complete and destroy login activity once successful
            requireActivity().finish()
        })

        /*첫 화면 밀리 애니메이션 띄우기*/
        front_img.setBackgroundResource(R.drawable.mily_animated)
        val animationDrawable =  front_img.background as AnimationDrawable
        animationDrawable.start()

        /*이메일 확인*/
        et_login_username.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    et_login_username.text.toString(),
                    et_login_password.text.toString()
                )
            }
        }

        /*비밀번호 확인*/
        et_login_password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    et_login_username.text.toString(),
                    et_login_password.text.toString()
                )
            }
            setOnEditorActionListener{
                _, actionId, _ ->
                when(actionId){
                    EditorInfo.IME_ACTION_DONE -> loginViewModel.login(
                        loginInfoBody = LoginInfoBody(
                            et_login_username.text.toString(), et_login_password.text.toString()
                        )
                    )
                }
                false
            }
        }

        /*로그인하기 버튼을 누른다 */
        btn_login_user_login.setOnClickListener {
            Log.d(TAG, "onActivityCreated: 로그인 버튼 프레스")
            loading.visibility = View.VISIBLE

            val loginInfoBody = LoginInfoBody(
                et_login_username.text.toString(), et_login_password.text.toString()
            )
            loginViewModel.login(
                loginInfoBody = loginInfoBody
            )
        }

        /* 카카오 세션을 오픈한다 */
        btn_kakao_login.setOnClickListener {
            val session = Session.getCurrentSession()
            session.addCallback(sessionCallback)
            session.open(AuthType.KAKAO_LOGIN_ALL, requireActivity())
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

    /*카카오톡 콜백 세션을 삭제한다. */
    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(sessionCallback)
    }

    /*카카오톡 간편로그인 실행 결과를 SDK 로 전달한다 */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(Session.getCurrentSession().handleActivityResult(
                requestCode, resultCode, data
            )){
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /*로그인이 실패하면 토스트를 띄운다 */
    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(requireContext(), errorString, Toast.LENGTH_SHORT).show()
    }

    /*로그인이 성공적이면 UI를 유저 정보를 활용해 업데이트 한다. */
    private fun updateUiWithUser(userToken: String) {

        Log.d(TAG, "updateUiWithUser: $userToken")

        val welcome = "환영합니다!"
        Toast.makeText(
            requireContext(),
            "$welcome",
            Toast.LENGTH_LONG
        ).show()

        val pref = requireActivity().getSharedPreferences(USER_TOKEN, MODE_PRIVATE)
        pref.edit().putString(USER_TOKEN, userToken).apply()

        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}