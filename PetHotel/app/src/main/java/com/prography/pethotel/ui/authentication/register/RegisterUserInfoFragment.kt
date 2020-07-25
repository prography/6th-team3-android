package com.prography.pethotel.ui.authentication.register

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.loader.content.CursorLoader
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.prography.pethotel.R
import com.prography.pethotel.models.GeneralUserInfo
import com.prography.pethotel.ui.MainActivity
import com.prography.pethotel.ui.authentication.afterTextChanged
import com.prography.pethotel.ui.authentication.utils.BaseFragment
import com.prography.pethotel.utils.AuthTokenViewModel
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.size
import kotlinx.android.synthetic.main.register_user_info_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


private const val TAG = "RegisterUserInfoFragment"


@Suppress("DEPRECATION")
class RegisterUserInfoFragment : BaseFragment() {

    private lateinit var registerViewModel: RegisterViewModel
    private val authTokenViewModel : AuthTokenViewModel by activityViewModels()

    private var generalUserInfo : GeneralUserInfo = GeneralUserInfo()
    private lateinit var userImage : ImageView
    private var profileImageFile : File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_user_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        checkPermission()

        registerViewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]

        userImage = img_register_user_image

        img_register_user_image.setOnClickListener {
            getAlbum()
        }

        /*다시 입력 화면으로 돌아왔을 때 정보가 남아있도록 한다.*/
        observeUserInfo()

        /* 정확한 정보 입력을 감지한다. */
        setListenersToFields()

        registerViewModel.registerFormState.observe(viewLifecycleOwner, Observer {
            btn_register_complete.isEnabled = it.isDataValid

            if(it.emailError != null){
                email_edit_text_field.error = it.emailError
            }
            if(it.nicknameError != null){
                nickname_edit_text_field.error = it.nicknameError
            }
            if(it.phoneNumberError != null){
                phone_edit_text_field.error = it.phoneNumberError
            }
            if(it.passwordError != null){
                password_check_edit_text_field.error = it.passwordError
            }
        })

        /*회원가입 완료 클릭시, 어느 한 필드라도 비어있으면 토스트를 띄운다.
        * 만약 모든 필드가 기입되어 있으면 서버로 회원가입을 진행한다. */
        btn_register_complete.setOnClickListener {
            if(isInputFieldNullOrBlank()){
                Toast.makeText(requireContext(), "정보를 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else{
                registerUser()
            }
        }

        //이미회원이신가요? 로그인하기 클릭하면 넘어가는 로직
        tv_login_from_register_screen.setOnClickListener {
            findNavController().navigate(R.id.action_registerUserInfoFragment_to_loginRegisterFragment)
        }

    }

    private fun isInputFieldNullOrBlank() : Boolean{
        return (email_edit_text_field.text.isNullOrBlank() ||
                nickname_edit_text_field.text.isNullOrBlank() ||
                password_edit_text_field.text.isNullOrBlank() ||
                password_check_edit_text_field.text.isNullOrBlank() ||
                phone_edit_text_field.text.isNullOrBlank())
    }

    private fun observeUserInfo(){
        registerViewModel.userInfo.observe(viewLifecycleOwner, Observer {
            Log.d("REGISTER", "register viewmodel called $it")
            email_edit_text_field.setText(it.email)
            nickname_edit_text_field.setText(it.nickName)
            phone_edit_text_field.setText(it.phoneNumber)
            password_edit_text_field.setText(it.password)
            password_check_edit_text_field.setText(it.password)
        })

    }

    private fun setListenersToFields(){
        email_edit_text_field.apply {
            afterTextChanged {
                registerViewModel.registerDataChanged(
                    nickname = nickname_edit_text_field.text.toString(),
                    email = email_edit_text_field.text.toString(),
                    phone = phone_edit_text_field.text.toString(),
                    password = password_edit_text_field.text.toString(),
                    passwordCheck = password_check_edit_text_field.text.toString()
                )
            }
        }
        nickname_edit_text_field.apply {
            afterTextChanged {
                registerViewModel.registerDataChanged(
                    nickname = nickname_edit_text_field.text.toString(),
                    email = email_edit_text_field.text.toString(),
                    phone = phone_edit_text_field.text.toString(),
                    password = password_edit_text_field.text.toString(),
                    passwordCheck = password_check_edit_text_field.text.toString()
                )
            }
        }
        phone_edit_text_field.apply {
            afterTextChanged {
                registerViewModel.registerDataChanged(
                    nickname = nickname_edit_text_field.text.toString(),
                    email = email_edit_text_field.text.toString(),
                    phone = phone_edit_text_field.text.toString(),
                    password = password_edit_text_field.text.toString(),
                    passwordCheck = password_check_edit_text_field.text.toString()
                )
            }
        }
        password_edit_text_field.apply {
            afterTextChanged {
                registerViewModel.registerDataChanged(
                    nickname = nickname_edit_text_field.text.toString(),
                    email = email_edit_text_field.text.toString(),
                    phone = phone_edit_text_field.text.toString(),
                    password = password_edit_text_field.text.toString(),
                    passwordCheck = password_check_edit_text_field.text.toString()
                )
            }
        }
        password_check_edit_text_field.apply {
            afterTextChanged {
                registerViewModel.registerDataChanged(
                    nickname = nickname_edit_text_field.text.toString(),
                    email = email_edit_text_field.text.toString(),
                    phone = phone_edit_text_field.text.toString(),
                    password = password_edit_text_field.text.toString(),
                    passwordCheck = password_check_edit_text_field.text.toString()
                )
            }
        }
    }

    private fun registerUser(){

        if(profileImageFile != null){
            val requestImageFile
                    = RequestBody.create("image/jpeg".toMediaTypeOrNull(), profileImageFile!!)
            Log.d(TAG, "registerUser: 이미지 파일=>  $requestImageFile")
            val multipartBody = MultipartBody.Part.createFormData(
                "profileImage",
                "profile.Image.jpg",
                requestImageFile
            )
            Log.d(TAG, "registerUser: 멀티파트 바디 => $multipartBody")

            generalUserInfo = GeneralUserInfo(
                nickName = nickname_edit_text_field.text.toString(),
                email = email_edit_text_field.text.toString(),
                phoneNumber = phone_edit_text_field.text.toString(),
                password = password_edit_text_field.text.toString(),
                userImagePart = multipartBody
            )

            registerViewModel.setUserInfo(generalUserInfo)

            val map = HashMap<String, RequestBody>()
            val nickName : RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                generalUserInfo.nickName.toString()
            )

            val phoneNumber : RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                generalUserInfo.phoneNumber.toString()
            )

            val email : RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                generalUserInfo.email.toString()
            )

            val password : RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                generalUserInfo.password.toString()
            )

            map["data[nickname]"] = nickName
            map["data[phoneNumber]"] = phoneNumber
            map["data[email]"] = email
            map["data[password]"] = password

            Log.d(TAG, "registerUser: 이미지 있음 => $generalUserInfo")

            registerViewModel.registerUserGeneralForm(
                registerUserInfo = map,
                photoUrl = multipartBody
            )
        }else{

            generalUserInfo = GeneralUserInfo(
                nickName = nickname_edit_text_field.text.toString(),
                email = email_edit_text_field.text.toString(),
                phoneNumber = phone_edit_text_field.text.toString(),
                password = password_edit_text_field.text.toString()
            )

            registerViewModel.setUserInfo(generalUserInfo)

            val map = HashMap<String, RequestBody>()
            val nickName : RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                generalUserInfo.nickName.toString()
            )

            val phoneNumber : RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                generalUserInfo.phoneNumber.toString()
            )

            val email : RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                generalUserInfo.email.toString()
            )

            val password : RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                generalUserInfo.password.toString()
            )

            map["data[nickname]"] = nickName
            map["data[phoneNumber]"] = phoneNumber
            map["data[email]"] = email
            map["data[password]"] = password

            Log.d(TAG, "registerUser: 이미지 없음 => $generalUserInfo")
            registerViewModel.registerUserGeneralForm(
                registerUserInfo = map,
                photoUrl = null
            )
        }

        registerViewModel.getRegisterStatus().observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onActivityCreated: register status = $it")

            if(it == true){
                Log.d(TAG, "registerUser: 회원가입 성공")
                registerViewModel.getUserToken().observe(viewLifecycleOwner, Observer {
                        userToken ->
                    Log.d(TAG, "onActivityCreated: $userToken")

                    // TODO: 7/24/2020 회원가입 성공하면 set token => Main Activity
                    authTokenViewModel.setUserToken(requireActivity(), userToken.token)
                })
                //different redirection based on register type
                redirectToMainActivity()
            }else{
                //FAIL REGISTRATION
                Log.d(TAG, "registerUser: 회원가입 실패")
                Toast.makeText(requireContext(),
                    getString(R.string.registration_fail_msg),
                    Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun redirectToMainActivity(){
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_TAKE_ALBUM && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                currentPhotoPath = data.data.toString()
                Log.d(TAG, "onActivityResult: $currentPhotoPath")

                if(data.data != null){
                    Glide.with(requireContext())
                    .load(Uri.parse(currentPhotoPath))
                    .into(userImage)

                    upload_icon.visibility = View.GONE

                    val uri = data.data
                    val realPath = getRealPathFromUri(uri!!)
                    if(!realPath.isNullOrEmpty()){
                        /* 원본 사진을 가져온다 */
                        val sourceFile = File(realPath)
                        Log.d(TAG, "onActivityResult: $sourceFile")

                        // TODO: 7/24/2020 회원 사진 올리는 것 테스트하기
                        GlobalScope.launch {
                            val result = compressImage(sourceFile)
                            profileImageFile = result
                            Log.d(TAG, "onActivityResult: Compression => $profileImageFile")
                        }
                    }
                }
            }
        }
    }

    private suspend fun compressImage(sourceFile : File) =
        withContext(Dispatchers.Default) {
            Compressor.compress(requireContext(), sourceFile){
                size( 1_000_000)
            }
        }

    private fun getRealPathFromUri(contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(requireContext(), contentUri, proj, null, null, null)
        val cursor: Cursor? = loader.loadInBackground()
        val columnIndex =
            cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val result = columnIndex?.let { cursor.getString(it) }
        cursor?.close()
        return result
    }
//2020-07-23 18:24:52.309 10676-10767/com.prography.pethotel D/OkHttp:
// {"id":134,"name":"\"withI7\"","email":"\"withImage7@naver.com\"","phoneNumber":"\"01022229999\"",
// "profileImage":"https://mypetmily-photo.s3.ap-northeast-2.amazonaws.com/user/20200723092452"}
}
