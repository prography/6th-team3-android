package com.prography.pethotel.ui.register

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.prography.pethotel.R
import com.prography.pethotel.ui.register.viewmodels.RegisterAuthPhoneViewModel
import kotlinx.android.synthetic.main.register_auth_phone_fragment.*

class RegisterAuthPhoneFragment : Fragment() {

    companion object {
        fun newInstance() =
            RegisterAuthPhoneFragment()
    }

    private lateinit var viewModel: RegisterAuthPhoneViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_auth_phone_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterAuthPhoneViewModel::class.java)
        // TODO: Use the ViewModel

        btn_register_auth_phone_next_screen.setOnClickListener {
            findNavController().navigate(R.id.action_registerAuthPhoneFragment_to_registerPetInfo)
        }
    }

}
