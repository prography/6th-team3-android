package com.prography.pethotel.ui.authentication.kakao

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.prography.pethotel.R
import com.prography.pethotel.api.auth.response.KakaoRegistrationResponse
import com.prography.pethotel.room.auth.AccountPropertiesViewModel
import com.prography.pethotel.ui.MainActivity
import com.prography.pethotel.ui.authentication.afterTextChanged
import com.prography.pethotel.utils.AuthTokenViewModel
import com.prography.pethotel.utils.AuthTokenViewModelFactory
import kotlinx.android.synthetic.main.fragment_kakao_register.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


private const val TAG = "KakaoRegisterFragment"
class KakaoRegisterFragment : Fragment() {

    private lateinit var kakaoRegisterViewModel: KakaoRegisterViewModel
    private lateinit var authTokenViewModel: AuthTokenViewModel
    private val accountPropertiesViewModel: AccountPropertiesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_kakao_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        kakaoRegisterViewModel = ViewModelProvider(requireActivity())[KakaoRegisterViewModel::class.java]
        authTokenViewModel = ViewModelProvider(requireActivity(), AuthTokenViewModelFactory())
            .get(AuthTokenViewModel::class.java)

        kakaoRegisterViewModel.kakaoRegisterUserData.observe(viewLifecycleOwner, Observer {
            setRegistrationData(it)
        })

        /*필드들의 값이 변하는 것을 관찰한다*/
        setListenersToFields()

        /*입력필드에 값들을 입력함에 따라서 에러 메시지를 표시한다.
        * 만약 모든 필드에 에러가 없으면 회원가입 완료 버튼이 enable 된다. */
        kakaoRegisterViewModel.registerFormState.observe(viewLifecycleOwner, Observer {

            btn_kakao_register_complete.isEnabled = it.isDataValid

            if(it.emailError != null){
                kakao_email_edit_text_field.error = it.emailError
            }
            if(it.nicknameError != null){
                kakao_nickname_edit_text_field.error = it.nicknameError
            }
            if(it.phoneNumberError != null){
                kakao_phone_edit_text_field.error = it.phoneNumberError
            }
            if(it.passwordError != null){
                kakao_password_check_edit_text_field.error = it.passwordError
            }
            Log.d(TAG, "onActivityCreated: ${it}")
        })

        /* 모든 필드에 에러가 없다는 것이 보장되므로 클릭리스너 안에서
        * 필드에 대한 에러 처리를 해 줄 필요가 없다.
        * 클릭하면, 입력된 필드들로 login 요청을 날리고, 유저 정보를 서버에 보낸다.
        * */
        btn_kakao_register_complete.setOnClickListener {

            val userId = kakaoRegisterViewModel
                .kakaoRegisterUserData.value?.data?.userId

            val profileImage = kakaoRegisterViewModel
                .kakaoRegisterUserData.value?.data?.information?.profileImage

            if(userId != null){

                val bUserId : RequestBody = userId.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())

                val bProfileImage = profileImage?.toRequestBody(
                    "text/plain".toMediaTypeOrNull()
                )
                val phoneNumber = kakao_phone_edit_text_field.text.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())
                val nickname = kakao_nickname_edit_text_field.text.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())

                val map = HashMap<String, RequestBody?>()
                map["data[nickname]"] = nickname
                map["data[phoneNumber]"] = phoneNumber
                map["data[profileImage]"] = bProfileImage
                map["userId"] = bUserId

                    kakaoRegisterViewModel.registerUserKakaoForm(
                        kakaoRegisterData = map
                    )
            }else{
                Toast.makeText(requireContext(), "카카오 인증 실패", Toast.LENGTH_SHORT).show()
            }
        }

        /*서버로 보낸 값들이 잘 입력되었는지 상태를 확인한다.
        * 상태 메시지에 따라서 성공 실패 토스트를띄워준다. */
        kakaoRegisterViewModel.getRegisterStatus().observe(viewLifecycleOwner, Observer {
            response ->
                if(response != null) {
                    if (response.status == "success") {
                        Toast.makeText(requireContext(), "회원가입 성공", Toast.LENGTH_SHORT).show()

                        // TODO: 7/24/2020 카카오 회원가입 성공 set token => Main Activity
                        authTokenViewModel.setUserToken(
                            requireActivity(),
                            response.userToken.token
                        )
                        redirectToMainActivity()
                    } else {
                        Toast.makeText(requireContext(), "회원가입 실패", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(), "서버 오류", Toast.LENGTH_SHORT).show()
                }
        })
    }

    private fun redirectToMainActivity(){
        val intent = Intent(requireActivity(), MainActivity::class.java)
        requireActivity().startActivity(intent)
        requireActivity().finish()
    }

    private fun setRegistrationData(kakaoRegistrationResponse: KakaoRegistrationResponse){
        val nickname = kakaoRegistrationResponse.data.information.nickname
        val profileImage = kakaoRegistrationResponse.data.information.profileImage
        val email = kakaoRegistrationResponse.data.information.email

        Glide.with(requireContext())
            .load(profileImage)
            .placeholder(R.drawable.mily_excited)
            .error(R.drawable.mily_sleepy)
            .into(kakao_img_register_user_image)

        kakao_email_edit_text_field.setText(email)
        kakao_nickname_edit_text_field.setText(nickname)
    }

    private fun setListenersToFields(){
        kakao_email_edit_text_field.apply {
            afterTextChanged {
                kakaoRegisterViewModel.registerDataChanged(
                    nickname = kakao_nickname_edit_text_field.text.toString(),
                    email = kakao_email_edit_text_field.text.toString(),
                    phone = kakao_phone_edit_text_field.text.toString(),
                    password = kakao_password_edit_text_field.text.toString(),
                    passwordCheck = kakao_password_check_edit_text_field.text.toString()
                )
            }
        }
        kakao_nickname_edit_text_field.apply {
            afterTextChanged {
                kakaoRegisterViewModel.registerDataChanged(
                    nickname = kakao_nickname_edit_text_field.text.toString(),
                    email = kakao_email_edit_text_field.text.toString(),
                    phone = kakao_phone_edit_text_field.text.toString(),
                    password = kakao_password_edit_text_field.text.toString(),
                    passwordCheck = kakao_password_check_edit_text_field.text.toString()
                )
            }
        }
        kakao_phone_edit_text_field.apply {
            afterTextChanged {
                kakaoRegisterViewModel.registerDataChanged(
                    nickname = kakao_nickname_edit_text_field.text.toString(),
                    email = kakao_email_edit_text_field.text.toString(),
                    phone = kakao_phone_edit_text_field.text.toString(),
                    password = kakao_password_edit_text_field.text.toString(),
                    passwordCheck = kakao_password_check_edit_text_field.text.toString()
                )
            }
        }
        kakao_password_edit_text_field.apply {
            afterTextChanged {
                kakaoRegisterViewModel.registerDataChanged(
                    nickname = kakao_nickname_edit_text_field.text.toString(),
                    email = kakao_email_edit_text_field.text.toString(),
                    phone = kakao_phone_edit_text_field.text.toString(),
                    password = kakao_password_edit_text_field.text.toString(),
                    passwordCheck = kakao_password_check_edit_text_field.text.toString()
                )
            }
        }
        kakao_password_check_edit_text_field.apply {
            afterTextChanged {
                kakaoRegisterViewModel.registerDataChanged(
                    nickname = kakao_nickname_edit_text_field.text.toString(),
                    email = kakao_email_edit_text_field.text.toString(),
                    phone = kakao_phone_edit_text_field.text.toString(),
                    password = kakao_password_edit_text_field.text.toString(),
                    passwordCheck = kakao_password_check_edit_text_field.text.toString()
                )
            }
        }
    }
}