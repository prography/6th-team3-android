package com.prography.pethotel.ui.register

import android.graphics.drawable.AnimationDrawable
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.prography.pethotel.R
import com.prography.pethotel.ui.register.viewmodels.LoginRegisterViewModel
import kotlinx.android.synthetic.main.login_register_fragment.*

class LoginRegisterFragment : Fragment() {

    companion object {
        fun newInstance() =
            LoginRegisterFragment()
    }

    private lateinit var viewModel: LoginRegisterViewModel

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

        }

        btn_login_user_register.setOnClickListener {
            //회원가입 화면으로 이동한다.
            findNavController().navigate(R.id.action_loginRegisterFragment_to_registerUserInfoFragment)
        }

    }

}
