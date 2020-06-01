package com.prography.pethotel.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast

import com.prography.pethotel.R
import com.prography.pethotel.ui.MainActivity
import com.prography.pethotel.utils.TAG_PET_DETAIL
import kotlinx.android.synthetic.main.fragment_register_pet_info.view.*
import kotlinx.android.synthetic.main.pet_detail_layout.view.*
import kotlinx.android.synthetic.main.pet_detail_layout.view.btn_erase_pet_card


class RegisterPetInfoFragment : Fragment() {

    private var numPets : Int = 0

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


        //메인 화면으로 넘어가기 전에 동물 등록번호 확인 / 필드 작성 여부 확인하기.
        view.btn_add_pet_done.setOnClickListener{
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }

        // 건너뛰기 버튼 클릭 시 필드 비어있어도 그냥 메인 화면으로 넘어가기
        view.btn_skip_pet_register.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
        return view
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

                if(checkPetNumber(num)){
                    //if 동물 등록 번호 확인 되면,
                    // TODO 사진 정보도 같이 저장하기
                    savePetInfo()
                    register_pet_number_checked.visibility = View.VISIBLE
                    Toast.makeText(context, "환영해요 ${name}!❤", Toast.LENGTH_SHORT).show()
                }
                Log.d(TAG_PET_DETAIL, "$num / $name / $birthYear => make petInfo object")
            }

            // TODO 사진 앨범에서 가져오기 버튼 클릭 리스너
            btn_upload_pet_image.setOnClickListener {

            }

            // TODO 사진 촬영하기 버튼 클릭 리스너
            btn_take_pet_photo.setOnClickListener {

            }

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

    // TODO 매개변수로 PET_INFO 객체 받아서 DB & 서버에 저장하기
    private fun savePetInfo(){
        Log.d(TAG_PET_DETAIL, "saving pet info ... ")
    }

    //Returns True if the number is registered
    private fun checkPetNumber(petRegisterNumber: String) : Boolean{
        //TODO send pet register number to the server
        Log.d(TAG_PET_DETAIL, "checking pet register number ... ")
        return true
    }

    private fun removeCard(view : View, viewGroup: ViewGroup, tag : String){
        val viewToBeRemoved = view.findViewWithTag<LinearLayout>(tag)
        viewGroup.removeView(viewToBeRemoved)
    }

    companion object {
        const val LAYOUT_ID_ : String = "layout_id_"
    }

}
