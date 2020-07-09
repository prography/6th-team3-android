package com.prography.pethotel.ui.authentication.register

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

import com.prography.pethotel.R
import com.prography.pethotel.api.auth.request.LoginInfoBody
import com.prography.pethotel.api.auth.response.PetNumResponse
import com.prography.pethotel.models.PetInfo
import com.prography.pethotel.ui.MainActivity
import com.prography.pethotel.ui.authentication.login.LoginViewModel
import com.prography.pethotel.ui.authentication.utils.BaseFragment
import com.prography.pethotel.utils.TAG_PET_DETAIL
import com.prography.pethotel.utils.USER_TOKEN
import kotlinx.android.synthetic.main.fragment_register_pet_info.view.*
import kotlinx.android.synthetic.main.fragment_register_pet_info.view.pet_info_input_layout
import kotlinx.android.synthetic.main.pet_detail_layout.view.*
import kotlinx.android.synthetic.main.pet_detail_layout.view.btn_erase_pet_card


private const val TAG = "RegisterPetInfoFragment"

class RegisterPetInfoFragment : BaseFragment() {

    private var numPets : Int = 0
    companion object {
        const val LAYOUT_ID_ : String = "layout_id_"
    }

    lateinit var registerViewModel : RegisterViewModel
    lateinit var loginViewModel : LoginViewModel

    private val petList : ArrayList<PetInfo> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_register_pet_info, container, false)
        view.pet_info_input_layout.addView(createPetInfoInputFieldLayout(container))

        view.btn_add_pet_input_field.setOnClickListener {
            view.pet_info_input_layout.addView(createPetInfoInputFieldLayout(container))
        }

        view.btn_register_full_info_done.setOnClickListener{

            registerPetInfoToUser(petList)

            registerViewModel.getRegisterPetResponse().observe(viewLifecycleOwner, Observer {
                if(it.status == "success"){
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }else{
                    Toast.makeText(requireContext(), "펫 등록 실패!ㅠㅠ", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // 건너뛰기 버튼 클릭 시 필드 비어있어도 그냥 메인 화면으로 넘어가기
        view.btn_skip_pet_register.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        registerViewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]
    }

    /* pet 카드를 하나 증가시킨다. 타이틀을 +1하고, 해당 뷰에 클릭 리스너를 달아준다. */
    private fun createPetInfoInputFieldLayout(container : ViewGroup?) : View{
        ++numPets
        val view = LayoutInflater.from(context).inflate(R.layout.pet_detail_layout, container, false)
        view.tag = LAYOUT_ID_ + numPets
        view.tv_pet_card_title.text = getString(R.string.pet_no, numPets)
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
                if(name.isNullOrEmpty() ||
                        num.isNullOrEmpty() ||
                        birthYear.isNullOrEmpty()){
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
                val viewGroup = view.parent as ViewGroup
                viewGroup.removeView(view)
                --numPets;
                /* pet 이 하나 감소한 상태이므로, 펫 마리수를
                인덱스로 사용할 수 있다.*/
                petList.removeAt(numPets)
                /* pet list 에 대한 내용을 유저에게 업데이트 */
                registerViewModel.setPetInfoToUser(petList)

                for(x in 1 .. numPets) {
                    val view = viewGroup.findViewWithTag<LinearLayout>(LAYOUT_ID_ + x)
                    view?.tv_pet_card_title?.text = getString(R.string.pet_no, numPets)
                }
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
        registerViewModel.setPetInfoToUser(petList)
    }

    private fun registerPetInfoToUser(petList: ArrayList<PetInfo>){
        val pref = requireActivity().getSharedPreferences(USER_TOKEN, MODE_PRIVATE)
        val token = pref.getString(USER_TOKEN, "")

        registerViewModel.getUser(token!!)

        registerViewModel.getUserInfoResponse().observe(viewLifecycleOwner, Observer {
            if(it.id != 0){
                registerViewModel.registerPetToUser(userToken = token!!, userId = it.id, petList = petList)
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
