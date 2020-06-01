package com.prography.pethotel.ui.register

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.prography.pethotel.R
import com.prography.pethotel.models.UserInfo
import com.prography.pethotel.ui.register.viewmodels.RegisterViewModel
import com.prography.pethotel.utils.TAG_PHOTO
import kotlinx.android.synthetic.main.register_user_info_fragment.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class RegisterUserInfoFragment : Fragment() {

    companion object {
        fun newInstance() =
            RegisterUserInfoFragment()

        const val REQUEST_TAKE_PHOTO = 1;
        const val REQUEST_TAKE_ALBUM = 2;
        const val MY_PERMISSION_CAMERA = 10;
    }

    private lateinit var viewModel: RegisterViewModel
    private var currentPhotoPath = ""
    private var userInfo : UserInfo = UserInfo()
    private lateinit var userImage : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_user_info_fragment, container, false)
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

        btn_register_next_screen.setOnClickListener {
            //TODO register - 추후에 로직 구체화하기
            if(!userInfo.email.isNullOrBlank()
                && !userInfo.nickName.isNullOrBlank()
                && !userInfo.password.isNullOrBlank()){
                // 비밀번호 인증 화면으로 넘어간다.
                viewModel.userInfoLiveData.value = userInfo
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


    private fun createImageFile() : File{
        // Create an image file name

        // Create an image file name
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File? =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.absolutePath
        return image
    }

    private fun checkPasswordMatch(password : String, checkPassword : String){
        if(password != checkPassword){
            tv_register_password_not_match.visibility = View.VISIBLE
        }else{
            tv_register_password_not_match.text = "비밀번호가 일치합니다."
            tv_register_password_not_match.setTextColor(resources.getColor(R.color.petHotelSecondary))
        }
    }


    private fun getAlbum(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, REQUEST_TAKE_ALBUM)
    }

    private fun checkPermission(){
        if (context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.CAMERA
                )
            ) {
                context?.let {
                    AlertDialog.Builder(it)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton(
                            "설정"
                        ) { dialogInterface, i ->
                            val intent =
                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            intent.data = Uri.parse("package:" + requireContext().packageName)
                            startActivity(intent)
                        }
                        .setPositiveButton(
                            "확인"
                        ) { dialogInterface, i -> activity?.finish() }
                        .setCancelable(false)
                        .create()
                        .show()
                }
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    ),
                    MY_PERMISSION_CAMERA
                )
            }
        }
    }

    private fun takePhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    requireContext(),
                    requireContext().packageName,
                    photoFile
                )
                Log.d(TAG_PHOTO, "photo uri : $photoURI")
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            }
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


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            MY_PERMISSION_CAMERA -> {
                for (i in grantResults.indices) {
                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                    if (grantResults[i] < 0) {
                        Toast.makeText(context, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT)
                            .show()
                        return
                    }
                }
            }//end of my_permission_camera

        }
    }

}
