package com.prography.pethotel.ui.mypage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide

import com.prography.pethotel.R
import com.prography.pethotel.api.auth.request.CheckPetBody
import com.prography.pethotel.api.auth.request.PetData
import com.prography.pethotel.room.auth.AccountPropertiesViewModel
import com.prography.pethotel.room.entities.Pet
import com.prography.pethotel.ui.authentication.register.RegisterViewModel
import com.prography.pethotel.ui.authentication.utils.BaseFragment
import com.prography.pethotel.utils.AuthTokenViewModel
import com.prography.pethotel.utils.TAG_PET_DETAIL
import kotlinx.android.synthetic.main.fragment_register_pet_info.view.*
import kotlinx.android.synthetic.main.pet_detail_layout.view.*
import kotlinx.android.synthetic.main.pet_detail_layout.view.btn_erase_pet_card
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


private const val TAG = "RegisterPetInfoFragment"

class RegisterPetInfoFragment : BaseFragment() {

    private var numPets : Int = 0
    companion object {
        const val LAYOUT_ID_ : String = "layout_id_"
    }

    private val registerViewModel : RegisterViewModel by activityViewModels()
    private val accountPropertiesViewModel: AccountPropertiesViewModel by activityViewModels()
    private val authTokenViewModel: AuthTokenViewModel by activityViewModels()

    private val checkedPetList : ArrayList<PetData> = ArrayList()
    private var token : String = ""
    var name : String = ""
    var birthYear : Int = 2005
    var petPhotoFile : MultipartBody.Part? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_register_pet_info, container, false)
        view.pet_info_input_layout.addView(createPetInfoInputFieldLayout(container))

        view.btn_add_pet_input_field.setOnClickListener {
            numPets += 1;
            view.pet_info_input_layout.addView(createPetInfoInputFieldLayout(container))
        }

           view.btn_register_full_info_done.setOnClickListener{
                if(token != "" && !checkedPetList.isNullOrEmpty()) {
//                    registerPetInfoToUser(token, checkedPetList)
                }else{
                    Log.d(TAG, "onCreateView: token or pet list is empty")
                }
            }
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        checkPermission()

        token = authTokenViewModel.getUserToken(requireActivity())
        accountPropertiesViewModel.fetchUser(token)

        registerViewModel.getRegisterPetResponse().observe(viewLifecycleOwner, Observer {
            when {
                it == null -> {
                    Log.d(TAG, "onCreateView: 펫 등록 결과가 null 입니다")
                    Toast.makeText(requireContext(), "펫 등록 실패! (서버 에러)", Toast.LENGTH_SHORT).show()
                }
                it.status == "success" -> {
                    Toast.makeText(requireContext(), "펫 등록 성공!", Toast.LENGTH_SHORT).show()
                    //findNavController().navigate(R.id.action_registerPetInfoFragment_to_petInfoFragment)
                }
                else -> {
                    Toast.makeText(requireContext(), "펫 등록 실패! (status ${it.status})", Toast.LENGTH_SHORT).show()
                }
            }
        })

        registerViewModel.getCheckPetResponse().observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "setOnClickListenerOnPetCard: onChanged... ")
            Log.d(TAG, "onActivityCreated: $it")

            if(it != null){
                //register pet right away

                //registerPetInfoToUser()

//                Log.d(TAG, "setOnClickListenerOnPetCard: $it")
//                checkedPetList.add(
//                    PetData(
//                        petName = name,
//                        registerNumber = it.registerNumber,
//                        birthYear = birthYear,
//                        breed = it.breed,
//                        isNeutered = it.isNeutered,
//                        gender = it.gender,
//                        rfidCode = it.registerNumber
//                    )
//                )
            }
        })
    }

    /* pet 카드를 하나 증가시킨다. 타이틀을 +1하고, 해당 뷰에 클릭 리스너를 달아준다. */
    private fun createPetInfoInputFieldLayout(container : ViewGroup?) : View{
        numPets += 1
        val view = LayoutInflater.from(context).inflate(R.layout.pet_detail_layout, container, false)
        view.tag = LAYOUT_ID_ + numPets
        setOnClickListenerOnPetCard(view)
        return view
    }

    /*만들어진 카드에 클릭 리스너를 달아준다. */
    private fun setOnClickListenerOnPetCard(view : View){
        Log.d(TAG_PET_DETAIL, "setting click listeners on ${view.tag}")
        view.apply {

            /* 등록번호확인 버튼을 눌렀을 때 */
            check_register_pet_number.setOnClickListener {

                /* 해당 뷰에 있는 Edit Text 를 찾아준다 */
                val num = view.et_register_pet_number.text.toString()
                name = view.et_register_pet_name.text.toString()
                birthYear = view.et_register_pet_birth_year.text.toString().toInt()

                /*필드 중 하나라도 비어있으면 정보 입력 하세요 토스트 */
                if(name.isEmpty() ||
                        num.isEmpty() ||
                        birthYear < 2000){
                    Toast.makeText(requireContext(), getString(R.string.enter_info_msg), Toast.LENGTH_SHORT).show()
                }
                else {
                   registerViewModel.checkPetNumber(
                       CheckPetBody(
                           petName = name,
                           registerNumber = num,
                           birthYear = birthYear
                       )
                    )
                }
            }

            img_register_pet_image.setOnClickListener {
                getAlbum(view = it)
            }

            /*유저가 펫카드를 지우는 경우 */
            btn_erase_pet_card.setOnClickListener {
                if(numPets > 1){
                    checkedPetList.remove(checkedPetList[--numPets])
                    val viewGroup = view.parent as ViewGroup
                    viewGroup.removeView(view)
                }
                else{
                    Toast.makeText(requireContext(),
                        "마지막 카드는 지울 수 없습니다!",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun registerPetInfoToUser(token : String, checkedPet : PetData){
        Log.d(TAG, "registerPetInfoToUser: $token $checkedPetList")

        accountPropertiesViewModel.userProperty.observe(viewLifecycleOwner, Observer {
            userResponse ->
            Log.d(TAG, "registerPetInfoToUser: $userResponse")

            if(userResponse.userId != 0){
                val map = HashMap<String, RequestBody>()
                map["data[petName]"] = checkedPet.petName
                    .toRequestBody("text/plain".toMediaTypeOrNull())
                map["data[registerNumber]"] = checkedPet.registerNumber
                    .toRequestBody("text/plain".toMediaTypeOrNull())
                map["data[year]"] = checkedPet.birthYear.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())
                map["data[breed]"] = checkedPet.breed
                    .toRequestBody("text/plain".toMediaTypeOrNull())
                map["data[isNeutered]"] = checkedPet.isNeutered.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())
                map["data[gender]"] = checkedPet.gender
                    .toRequestBody("text/plain".toMediaTypeOrNull())
                map["data[rfidCode]"] = checkedPet.rfidCode
                    .toRequestBody("text/plain".toMediaTypeOrNull())

                //map["photo"] = petPhotoFile

                registerViewModel.registerPetToUser(
                    token = token,
                    registerPetParts = map,
                    petProfileUrl = null
                )

                //결과를 관찰한다.
                registerViewModel.getRegisterPetResponse().observe(viewLifecycleOwner, Observer {
                    petResponse ->

                    when {
                        petResponse == null -> {
                            Log.d(TAG, "registerPetInfoToUser: REGISTER FAILED")
                        }
                        petResponse.status == "success" -> {
//                            Log.d(TAG, "registerPetInfoToUser: 펫 등록 성공")
//                            Log.d(TAG, "registerPetInfoToUser: ${petResponse.data[0].breed}")
//                            petResponse.data.forEach { petData ->
//                                accountPropertiesViewModel.insertPetToDb(
//                                    Pet(
//                                        petId = petData.id,
//                                        petName = petData.name,
//                                        petBirthYear = petData.year,
//                                        dogRegisterNo = petData.registerNum,
//                                        rfidCardNo = petData.rfidCode,
//                                        gender = petData.gender,
//                                        kind = petData.breed,
//                                        isNeutered = petData.isNeutered,
//                                        ownerId = userResponse.userId
//                                    )
//                                )
//                            }
                        }
                        else -> {
                            Log.d(TAG, "registerPetInfoToUser: REGISTER FAILED 2")
                        }
                    }
                })
            }else{
                Log.d(TAG, "registerPetInfoToUser: 유저 정보가 유효하지 않음")
            }
        })
    }
    //Returns True if the number is registered

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == REQUEST_TAKE_ALBUM && resultCode == Activity.RESULT_OK) {
//            if (data != null) {
//                currentPhotoPath = data.data.toString()
//                Log.d(TAG, "onActivityResult: $currentPhotoPath")
//
//                if(data.data != null){
//                    Glide.with(requireContext())
//                        .load(Uri.parse(currentPhotoPath))
//                        .into(userImage)
//
//                    val uri = data.data
//                    val realPath = getRealPathFromUri(uri!!)
//                    if(!realPath.isNullOrEmpty()){
//                        val file = File(realPath)
//                        Log.d(com.prography.pethotel.ui.authentication.register.TAG, "onActivityResult: $file")
//                        ///storage/emulated/0/DCIM/Camera/IMG_20200601_223652.jpg
//                        if(file != null){
//                            profileImageFile = file
//                        }
//                    }
//                }
//            }
//        }
    }
}
