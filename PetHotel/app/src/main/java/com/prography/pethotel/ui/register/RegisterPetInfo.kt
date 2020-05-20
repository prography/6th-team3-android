package com.prography.pethotel.ui.register

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController

import com.prography.pethotel.R
import com.prography.pethotel.ui.register.RegisterPetInfoDirections
import com.prography.pethotel.ui.register.viewmodels.RegisterPetInfoViewModel
import kotlinx.android.synthetic.main.register_pet_info_fragment.*

class RegisterPetInfo : Fragment() {

    companion object {
        fun newInstance() = RegisterPetInfo()
    }

    private lateinit var viewModel: RegisterPetInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_pet_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterPetInfoViewModel::class.java)

        var numOfPets = 1
        et_register_pet_how_many_pets.setText("1") // default 로 1마리, NumberFormatException 방지

        et_register_pet_how_many_pets.addTextChangedListener(
            afterTextChanged = {
                numOfPets = it.toString().toInt()
            }
        )
        btn_register_pet_enter_info.setOnClickListener {
            // 몇 마리 입력했는지 받아와서 다음 화면에 정보 전달해주기.
            Log.d(
                "PETS", numOfPets.toString()
            )
            val action =
                RegisterPetInfoDirections.actionRegisterPetInfoToRegisterPetInfoDetailFragment(
                    numOfPets
                )
            findNavController().navigate(action)
        }
    }

}
