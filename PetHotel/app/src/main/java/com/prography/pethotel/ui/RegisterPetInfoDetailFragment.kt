package com.prography.pethotel.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager

import com.prography.pethotel.R
import com.prography.pethotel.models.PetInfo
import kotlinx.android.synthetic.main.register_pet_info_detail_fragment.*

class RegisterPetInfoDetailFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterPetInfoDetailFragment()
    }

    private lateinit var viewModel: RegisterPetInfoDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_pet_info_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterPetInfoDetailViewModel::class.java)


        // 몇 마리 입력하는 건지 이전 화면에서 받아오기
        // 동적으로 레이아웃 추가할 수 있도록 하기 ?

    }


}

