package com.prography.pethotel.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.prography.pethotel.R

class RegisterAuthPhoneFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterAuthPhoneFragment()
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
    }

}
