package com.prography.pethotel.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.prography.pethotel.R
import kotlinx.android.synthetic.main.fragment_pet_info.*


class PetInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pet_info, container, false)
        //TODO 만약 펫 정보가 있다면 없음 화면 대신 카드 리스트를 띄워주기
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fab_add_pet.setOnClickListener {
            findNavController().navigate(R.id.action_petInfoFragment_to_registerPetFragment)
        }

    }
}