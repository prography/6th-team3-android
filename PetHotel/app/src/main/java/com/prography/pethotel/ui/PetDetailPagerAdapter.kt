package com.prography.pethotel.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.prography.pethotel.R
import com.prography.pethotel.models.PetInfo
import kotlinx.android.synthetic.main.pet_info_layout.view.*

class PetDetailPagerAdapter(
    private val context: Context?,
    private val petInfoArrayList: ArrayList<PetInfo>
) : PagerAdapter(){

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return `object` == view
    }

    override fun getCount(): Int {
        return petInfoArrayList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view : View =
            LayoutInflater.from(context).inflate(R.layout.pet_info_layout, container, false)

        view.btn_register_pet_info_enter.setOnClickListener {
            // 입력된 정보 저장하기
            // 누르기 전에 다른 정보 입력 화면으로 돌아왔을때도 정보 살아있도록 LiveData 로 관리하기
            val petName : String = view.et_register_pet_name.text.toString()
            val petNumber : String = view.et_register_pet_number.text.toString()
            val petBirthYear : String = view.et_register_pet_birth_year.text.toString()
            //받아온 정보 저장하기
            petInfoArrayList[position].name = petName
            petInfoArrayList[position].registrationNum = petNumber
            petInfoArrayList[position].birthYear = petBirthYear
        }

        view.btn_register_pet_info_enter_more.setOnClickListener {
            //다음 페이지로 넘어가기. 만약 마지막 화면이면 버튼 없애기.
        }

        container.addView(view)
        return view
    }

}