package com.prography.pethotel.ui.mypage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.prography.pethotel.R
import com.prography.pethotel.models.UserInfo
import com.prography.pethotel.ui.register.RegisterUserInfoFragment
import com.prography.pethotel.ui.register.utils.BaseFragment
import com.prography.pethotel.ui.register.viewmodels.RegisterViewModel
import com.prography.pethotel.utils.TAG_PHOTO
import kotlinx.android.synthetic.main.my_page_user_info_edit_fragment.*
import kotlinx.android.synthetic.main.register_user_info_fragment.*
import kotlinx.android.synthetic.main.register_user_info_fragment.btn_take_user_photo
import kotlinx.android.synthetic.main.register_user_info_fragment.btn_upload_user_image
import kotlinx.android.synthetic.main.register_user_info_fragment.email_edit_text_field
import kotlinx.android.synthetic.main.register_user_info_fragment.img_register_user_image
import kotlinx.android.synthetic.main.register_user_info_fragment.nickname_edit_text_field
import kotlinx.android.synthetic.main.register_user_info_fragment.password_check_edit_text_field
import kotlinx.android.synthetic.main.register_user_info_fragment.password_edit_text_field
import kotlinx.android.synthetic.main.register_user_info_fragment.tv_register_password_not_match

class UserInfoEditFragment : BaseFragment() {

    companion object {
        fun newInstance() =
            UserInfoEditFragment()
    }

    private lateinit var viewModel: RegisterViewModel
    //    private var currentPhotoPath = ""
    private var userInfo : UserInfo = UserInfo()
    private lateinit var userImage : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_page_user_info_edit_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        userImage = img_register_user_image

        viewModel.userInfoLiveData.observe(viewLifecycleOwner, Observer {
            Log.d("REGISTER", "register viewmodel called $it")
            email_edit_text_field.setText(it.email)
            nickname_edit_text_field.setText(it.nickName)
            password_edit_text_field.setText(it.password)
            password_check_edit_text_field.setText(it.password)
        })

        btn_upload_user_image.setOnClickListener {
            getAlbum()
        }

        btn_take_user_photo.setOnClickListener {
            takePhoto()
        }

        btn_edit_finished.setOnClickListener {
            //TODO register - 추후에 로직 구체화하기
//            if(!userInfo.email.isNullOrBlank()
//                && !userInfo.nickName.isNullOrBlank()
//                && !userInfo.password.isNullOrBlank()){
//                // 비밀번호 인증 화면으로 넘어간다.
//                viewModel.userInfoLiveData.value = userInfo
//                findNavController().navigate(R.id.action_registerUserInfoFragment_to_registerAuthPhoneFragment)
//            }else{
//                Toast.makeText(context, "정보를 입력해 주세요.", Toast.LENGTH_LONG).show()
//            }
            // 비밀번호 인증 화면으로 넘어간다.
            findNavController().navigate(R.id.action_userInfoEditFragment_to_myPageFragment)

        }

        setUpUserInfoInputListeners()
    }


    private fun setUpUserInfoInputListeners(){
        email_edit_text_field.addTextChangedListener(
            afterTextChanged = {
                userInfo.email = it.toString()
                Log.d("REGISTER", viewModel.userInfoLiveData.toString())
            }
        )

        nickname_edit_text_field.addTextChangedListener(
            afterTextChanged = {
                userInfo.nickName = it.toString()
                Log.d("REGISTER", viewModel.userInfoLiveData.toString())
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
                Log.d("REGISTER", viewModel.userInfoLiveData.toString())
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {

            Log.d(TAG_PHOTO, "${currentPhotoPath}")
//            Glide.with(requireContext())
//                .load(Uri.parse(currentPhotoPath))
//                .into(userImage)
            userImage.setImageURI(Uri.parse(currentPhotoPath))

        } else if (requestCode == REQUEST_TAKE_ALBUM && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                currentPhotoPath = data.data.toString()

                Glide.with(requireContext())
                    .load(Uri.parse(currentPhotoPath))
                    .into(userImage)
            }

        }
    }

}
