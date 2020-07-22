package com.prography.pethotel.ui.mypage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.prography.pethotel.R
import com.prography.pethotel.room.AppDatabase
import com.prography.pethotel.room.auth.AccountPropViewModelFactory
import com.prography.pethotel.room.auth.AccountPropertiesViewModel
import com.prography.pethotel.utils.AuthTokenViewModel
import com.prography.pethotel.utils.AuthTokenViewModelFactory
import kotlinx.android.synthetic.main.fragment_user_info_edit.*


private const val TAG = "UserInfoEditFragment"
class UserInfoEditFragment : Fragment() {

    private lateinit var accountPropertiesViewModel: AccountPropertiesViewModel
    private lateinit var authTokenViewModel: AuthTokenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info_edit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val database = AppDatabase.getInstance(requireContext())
        val accountDao = database.accountPropertiesDao

        accountPropertiesViewModel = ViewModelProvider(
            requireActivity(),
            AccountPropViewModelFactory(accountDao, requireActivity().application)
        )[AccountPropertiesViewModel::class.java]

        authTokenViewModel = ViewModelProvider(requireActivity(), AuthTokenViewModelFactory())
                            .get(AuthTokenViewModel::class.java)

        val token = authTokenViewModel.getUserToken(requireActivity())
        accountPropertiesViewModel.fetchUser(token)

        accountPropertiesViewModel.userProperty.observe(viewLifecycleOwner, Observer {
            if(it == null){
                Log.d(TAG, "onActivityCreated: user token is invalid $token")
            }else{
                edit_user_email.setText("set the email")
                edit_user_phone.setText(it.phoneNumber)

                Glide.with(requireContext())
                    .load(it.profileImage)
                    .error(R.drawable.pet_profile_placeholder)
                    .placeholder(R.drawable.pet_profile_placeholder)
                    .into(edit_user_image)
            }
        })

    }
}