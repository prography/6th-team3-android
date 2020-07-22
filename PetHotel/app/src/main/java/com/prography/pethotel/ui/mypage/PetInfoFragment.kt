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
        return inflater.inflate(R.layout.fragment_pet_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fab_add_pet.setOnClickListener {
            findNavController().navigate(R.id.action_petInfoFragment_to_registerPetInfoFragment)
        }

    }
}