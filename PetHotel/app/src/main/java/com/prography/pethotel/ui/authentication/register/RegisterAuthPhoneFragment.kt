package com.prography.pethotel.ui.authentication.register
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.navigation.fragment.findNavController
//
//import com.prography.pethotel.R
//import kotlinx.android.synthetic.main.register_auth_phone_fragment.*
//
///*This class will be deleted */
//class RegisterAuthPhoneFragment : Fragment() {
//
//    companion object {
//        fun newInstance() =
//            RegisterAuthPhoneFragment()
//    }
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.register_auth_phone_fragment, container, false)
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//
//        btn_register_auth_phone_next_screen.setOnClickListener {
//            findNavController().navigate(R.id.action_registerAuthPhoneFragment_to_registerPetInfoFragment)
//        }
//    }
//}
