package com.prography.pethotel.ui.mypage

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.prography.pethotel.R
import com.prography.pethotel.api.auth.response.PetNumResponse
import com.prography.pethotel.models.PetInfo
import com.prography.pethotel.room.AppDatabase
import com.prography.pethotel.room.auth.AccountPropViewModelFactory
import com.prography.pethotel.room.auth.AccountPropertiesViewModel
import com.prography.pethotel.room.entities.Pet
import com.prography.pethotel.ui.MainActivity
import com.prography.pethotel.ui.authentication.login.LoginViewModel
import com.prography.pethotel.ui.authentication.register.RegisterViewModel
import com.prography.pethotel.ui.authentication.utils.BaseFragment
import com.prography.pethotel.utils.AuthTokenViewModel
import com.prography.pethotel.utils.AuthTokenViewModelFactory
import com.prography.pethotel.utils.TAG_PET_DETAIL
import com.prography.pethotel.utils.USER_TOKEN
import kotlinx.android.synthetic.main.fragment_register_pet_info.view.*
import kotlinx.android.synthetic.main.pet_detail_layout.view.*
import kotlinx.android.synthetic.main.pet_detail_layout.view.btn_erase_pet_card


private const val TAG = "RegisterPetInfoFragment"

class RegisterPetInfoFragment : BaseFragment() {

    private var numPets : Int = 0
    companion object {
        const val LAYOUT_ID_ : String = "layout_id_"
    }

    lateinit var registerViewModel : RegisterViewModel
    lateinit var accountPropertiesViewModel: AccountPropertiesViewModel
    lateinit var authTokenViewModel: AuthTokenViewModel

    private val petList : ArrayList<PetInfo> = ArrayList()

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
            registerPetInfoToUser(petList)
            registerViewModel.getRegisterPetResponse().observe(viewLifecycleOwner, Observer {
                when {
                    it == null -> {
                        Log.d(TAG, "onCreateView: 펫 등록 결과가 null 입니다")
                    }
                    it.status == "success" -> {
                        Toast.makeText(requireContext(), "펫 등록 성공!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_registerPetInfoFragment_to_petInfoFragment)
                    }
                    else -> {
                        Toast.makeText(requireContext(), "펫 등록 실패!", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        registerViewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]

        /* 계정 정보 관련 뷰모델 초기화 */
        val database = AppDatabase.getInstance(requireContext())
        val accountDao = database.accountPropertiesDao
        accountPropertiesViewModel = ViewModelProvider(requireActivity(),
        AccountPropViewModelFactory(
            accountPropertiesDao = accountDao,
            application = requireActivity().application
        ))[AccountPropertiesViewModel::class.java]

        authTokenViewModel = ViewModelProvider(requireActivity()
            , AuthTokenViewModelFactory())[AuthTokenViewModel::class.java]
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
            check_register_pet_number.setOnClickListener {

                /* 해당 뷰에 있는 Edit Text 를 찾아준다 */
                val num = view.et_register_pet_number.text.toString()
                val name = view.et_register_pet_name.text.toString()
                val birthYear = view.et_register_pet_birth_year.text.toString()

                /*필드 중 하나라도 비어있으면 정보 입력 하세요 토스트 */
                if(name.isEmpty() ||
                        num.isEmpty() ||
                        birthYear.isEmpty()){
                    Toast.makeText(requireContext(), getString(R.string.enter_info_msg), Toast.LENGTH_SHORT).show()
                }
                else {
                    /* 하나의 카드에서 저장해둔 내용들 */
                    val petInfo = PetInfo(name = name, registrationNum = num, birthYear = birthYear)

                    /* 동물등록번호 요청하고 응답 기다리기 */
                    registerViewModel.checkPetNumber(dogRegNo = num)
                    registerViewModel.getPetNumberResponse().observe(viewLifecycleOwner, Observer {

                        Log.d(TAG, "checkPetNumber: $it")

                        /* 동물등록번호 확인이 성공적인 경우 00 코드를 반환한다. */
                        if(it.header?.resultCode == "00"){
                            /* in memory cache */
                            savePetInfo(petInfo, it)

                            register_pet_number_checked.visibility = View.VISIBLE
                            Toast.makeText(requireContext(), "환영해요 ${name}!❤", Toast.LENGTH_SHORT)
                                .show()

                        /* 동물등록번호 확인이 실패하면 토스트 */
                        }else{
                            Toast.makeText(
                                requireContext(),
                                context.getString(R.string.animal_num_check_fail_msg),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
            }

//            btn_upload_pet_image.setOnClickListener {
//                Log.d(TAG_PHOTO, "${this.tag}")
//                getAlbum(view= this)
//            }
//
//            btn_take_pet_photo.setOnClickListener {
//                Log.d(TAG_PHOTO, "${this.tag}")
//                takePhoto(view= this)
//            }

            /*유저가 펫카드를 지우는 경우의 메서드.
            * 뷰그룹에서 카드를 remove 하고,
            * Pet 의 마리수를 줄이고 해당 Pet을 List 에서 삭제한다.
            * 유저 모델에서도 지워진 펫에대한 업데이트를 진행한다.
            */
            btn_erase_pet_card.setOnClickListener {
                numPets -= 1
                val viewGroup = view.parent as ViewGroup
                viewGroup.removeView(view)
                /* pet list 에 대한 내용을 유저에게 업데이트 */
                registerViewModel.setPetInfoToUser(petList)

            }
        }
    }

    /* 원래 유저가 입력한 정보와 동물등록번호 API 에서 받아오 정보들을
    * 합쳐서 펫 리스트에 저장한다. 유저 모델에도 해당 정보를 업데이트 한다. */
    private fun savePetInfo(petInfo: PetInfo, petNumResponse: PetNumResponse){
        petInfo.neuterYn = petNumResponse.body?.item?.neuterYn
        petInfo.sexNm = petNumResponse.body?.item?.sexNm
        petInfo.kindNm = petNumResponse.body?.item?.kindNm
        petInfo.orgNm = petNumResponse.body?.item?.orgNm
        petInfo.officeTel = petNumResponse.body?.item?.officeTel
        petInfo.aprGbNm = petNumResponse.body?.item?.aprGbNm

        petList.add(petInfo)
    }

    private fun registerPetInfoToUser(petList: ArrayList<PetInfo>){
        val token = authTokenViewModel.getUserToken(requireActivity())
        registerViewModel.getUser(token)
        registerViewModel.getUserInfoResponse().observe(viewLifecycleOwner, Observer {
            userResponse ->
            if(userResponse.id != 0){
                registerViewModel.registerPetToUser(
                    userToken = token,
                    userId = userResponse.id,
                    petList = petList
                )
                registerViewModel.getRegisterPetResponse().observe(viewLifecycleOwner, Observer {
                    petResponse ->

                    when {
                        petResponse == null -> {
                            Log.d(TAG, "registerPetInfoToUser: REGISTER FAILED")
                        }
                        petResponse.message == "success" -> {
                            Log.d(TAG, "registerPetInfoToUser: 펫 등록 성공")
                            Log.d(TAG, "registerPetInfoToUser: ${petResponse.data[0].breed}")
                            petResponse.data.forEach { petData ->
                                accountPropertiesViewModel.insertPetToDb(
                                    Pet(
                                        petId = petData.id,
                                        petName = petData.name,
                                        petBirthYear = petData.year,
                                        dogRegisterNo = petData.registerNum,
                                        rfidCardNo = petData.rfidCode,
                                        gender = petData.gender,
                                        kind = petData.breed,
                                        isNeutered = petData.isNeutered,
                                        ownerId = userResponse.id
                                    )
                                )
                            }
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


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
//            myView = pet_info_input_layout.findViewWithTag<LinearLayout>(myViewTag)
//            (myView as LinearLayout?)?.img_register_pet_image?.setImageURI(
//                Uri.parse(
//                    currentPhotoPath
//                )
//            )
//
//        } else if (requestCode == REQUEST_TAKE_ALBUM && resultCode == Activity.RESULT_OK) {
//            myView = pet_info_input_layout.findViewWithTag<LinearLayout>(myViewTag)
//            if (data != null) {
//                currentPhotoPath = data.data.toString()
//
//                (myView as LinearLayout?)?.img_register_pet_image?.let {
//                    Glide.with(requireContext())
//                        .load(Uri.parse(currentPhotoPath))
//                        .into(it)
//                }
//            }
//        }
//    }

}
