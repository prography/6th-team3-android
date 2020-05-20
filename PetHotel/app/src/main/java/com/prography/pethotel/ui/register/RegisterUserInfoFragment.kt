package com.prography.pethotel.ui.register

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.prography.pethotel.R
import com.prography.pethotel.models.UserInfo
import com.prography.pethotel.ui.register.viewmodels.RegisterViewModel
import kotlinx.android.synthetic.main.register_user_info_fragment.*

class RegisterUserInfoFragment : Fragment() {

    companion object {
        fun newInstance() =
            RegisterUserInfoFragment()
    }

    private lateinit var viewModel: RegisterViewModel
    private var userInfo : UserInfo = UserInfo()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_user_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        viewModel._userInfo.observe(viewLifecycleOwner, Observer {
            Log.d("REGISTER", "register viewmodel called $it")
            email_edit_text_field.setText(it.email)
            nickname_edit_text_field.setText(it.nickName)
            password_edit_text_field.setText(it.password)
            password_check_edit_text_field.setText(it.password)
        })

        img_register_user_image.setOnClickListener {
            //TODO register - 로컬 사진들 중 프로필 사진 고르고, Crop 하는 기능 추가하기

        }

        btn_register_next_screen.setOnClickListener {
            //TODO register - 추후에 로직 구체화하기
            if(!userInfo.email.isNullOrBlank()
                && !userInfo.nickName.isNullOrBlank()
                && !userInfo.password.isNullOrBlank()){
                // 비밀번호 인증 화면으로 넘어간다.
                viewModel._userInfo.value = userInfo
                findNavController().navigate(R.id.action_registerUserInfoFragment_to_registerAuthPhoneFragment)
            }else{
                Toast.makeText(context, "정보를 입력해 주세요.", Toast.LENGTH_LONG).show()
            }

        }

        setUpUserInfoInputListeners()
    }


    private fun setUpUserInfoInputListeners(){
        email_edit_text_field.addTextChangedListener(
            afterTextChanged = {
                userInfo.email = it.toString()
                Log.d("REGISTER", viewModel._userInfo.toString())
            }
        )

        nickname_edit_text_field.addTextChangedListener(
            afterTextChanged = {
                userInfo.nickName = it.toString()
                Log.d("REGISTER", viewModel._userInfo.toString())
            }
        )
        setUpPasswordInputListener()
    }

    private fun setUpPasswordInputListener(){
        password_edit_text_field.setOnFocusChangeListener { v, hasFocus ->
            val password = password_edit_text_field.text.toString()
            if(!hasFocus && password.isNotEmpty()){
                setUpPasswordCheckInputLister(password)
                userInfo.password = password
                Log.d("REGISTER", viewModel._userInfo.toString())
            }
        }
    }

    private fun setUpPasswordCheckInputLister(password: String) {
        password_check_edit_text_field.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus &&
                password.isEmpty()
            ){
                Toast.makeText(context, "사용하실 비밀번호를 먼저 입력해 주세요", Toast.LENGTH_LONG).show()
            }else if(hasFocus &&
                password.isNotEmpty()
            ){
                password_check_edit_text_field.addTextChangedListener(
                    onTextChanged = {
                            pw, _, _, _ -> checkPasswordMatch(password, pw.toString())
                        Log.d("REGISTER", pw.toString() + ", " + " Checking against " + password)
                    }
                )
            }
        }
    }

    private fun checkPasswordMatch(password : String, checkPassword : String){
        if(password != checkPassword){
            tv_register_password_not_match.visibility = View.VISIBLE
        }else{
            tv_register_password_not_match.text = "비밀번호가 일치합니다."
            tv_register_password_not_match.setTextColor(resources.getColor(R.color.petHotelSecondary))
        }
    }

}
