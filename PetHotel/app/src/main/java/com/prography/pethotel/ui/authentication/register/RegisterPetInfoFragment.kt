package com.prography.pethotel.ui.authentication.register

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
import com.prography.pethotel.models.PetInfo
import com.prography.pethotel.ui.MainActivity
import com.prography.pethotel.ui.authentication.utils.BaseFragment
import com.prography.pethotel.utils.TAG_PET_DETAIL
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
    private val petList : ArrayList<PetInfo> = ArrayList()
    private lateinit var petInfo: PetInfo

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


        view.btn_add_pet_done.setOnClickListener{
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
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

    private fun createPetInfoInputFieldLayout(container : ViewGroup?) : View{
        // Increment the number of pet cards
        ++numPets
        val view = LayoutInflater.from(context).inflate(R.layout.pet_detail_layout, container, false)
        view.tag = LAYOUT_ID_ + numPets
        view.tv_pet_card_title.text = getString(R.string.pet_no, numPets)
        setOnClickListenerOnPetCard(view)
        return view
    }

    private fun setOnClickListenerOnPetCard(view : View){
        Log.d(TAG_PET_DETAIL, "setting click listeners on ${view.tag}")
        view.apply {
            check_register_pet_number.setOnClickListener {

                val num = et_register_pet_number.text.toString()
                val name = et_register_pet_name.text.toString()
                val birthYear = et_register_pet_birth_year.text.toString()

                if(name.isNullOrEmpty() ||
                        num.isNullOrEmpty() ||
                        birthYear.isNullOrEmpty()){
                    Toast.makeText(requireContext(), getString(R.string.enter_info_msg), Toast.LENGTH_SHORT).show()
                }
                else {
                    //@Parcelize
                    //data class PetInfo(
                    //    var name : String? = "",
                    //    var registrationNum : String? = "",
                    //    var birthYear : String? = "",
                    //    val sexNm : String, //성별
                    //    val kindNm : String, //품종
                    //    val neuterYn : String, //중성화 여부
                    //    val orgNm : String, //관할 기관
                    //    val officeTel : String, //관할기관 연락처
                    //    val aprGbNm : String //승인 여부
                    //) : Parcelable
                    petInfo = PetInfo(name = name, registrationNum = num, birthYear = birthYear)

                    Log.d(TAG, "setOnClickListenerOnPetCard: $petInfo")

                    registerViewModel.checkPetNumber(dogRegNo = num)

                    registerViewModel.getPetNumberResponse().observe(viewLifecycleOwner, Observer {
                        Log.d(TAG, "checkPetNumber: $it")
                        if(it.header.resultCode == "00"){
                            //if 동물 등록 번호 확인 되면,
                            savePetInfo(petInfo)
                            register_pet_number_checked.visibility = View.VISIBLE
                            Toast.makeText(requireContext(), "환영해요 ${name}!❤", Toast.LENGTH_SHORT)
                                .show()
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

            btn_erase_pet_card.setOnClickListener {
                val viewGroup = view.parent as ViewGroup
                viewGroup.removeView(view)
                --numPets;
                for(x in 1 .. numPets) {
                    val view = viewGroup.findViewWithTag<LinearLayout>(LAYOUT_ID_ + x)
                    view?.tv_pet_card_title?.text = getString(R.string.pet_no, numPets)
                }
            }
        }
    }

    private fun savePetInfo(petInfo: PetInfo){
        petList.add(petInfo)
        registerViewModel.setPetInfoToUser(petList)
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
