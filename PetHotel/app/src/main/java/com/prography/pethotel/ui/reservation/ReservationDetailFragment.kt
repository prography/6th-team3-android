package com.prography.pethotel.ui.reservation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.prography.pethotel.R
import com.prography.pethotel.room.auth.AccountPropertiesViewModel
import com.prography.pethotel.room.entities.Pet
import com.prography.pethotel.utils.AuthTokenViewModel
import kotlinx.android.synthetic.main.fragment_reservation_detail.*
import kotlinx.android.synthetic.main.reservation_detail_check_hotel_layout.*
import kotlinx.android.synthetic.main.reservation_detail_check_user_info_layout.*
import kotlinx.android.synthetic.main.reservation_detail_pet_card.*


private const val TAG = "ReservationDetailFragme"
class ReservationDetailFragment : Fragment() {

    private val reservationViewModel : ReservationViewModel by activityViewModels()
    val authTokenViewModel : AuthTokenViewModel by activityViewModels()
    val accountPropertiesViewModel : AccountPropertiesViewModel by activityViewModels()

    var petList : List<Pet> = listOf()
    var userId : Int = -1

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val view = inflater.inflate(R.layout.fragment_reservation_detail, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userId = authTokenViewModel.getUserId(requireActivity())
        val token = authTokenViewModel.getUserToken(requireActivity())
        accountPropertiesViewModel.fetchPetByUserId(userId)
        accountPropertiesViewModel.fetchUser(token)

        accountPropertiesViewModel.petListResult.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onActivityCreated: pet 정보 불러오기 완료 ")
            petList = it
            if(petList.isNotEmpty()){
                val pet = petList[0]
                tv_reservation_detail_pet_name.text = pet.petName
                tv_reservation_detail_pet_kind.text = pet.kind
            }
        })

        accountPropertiesViewModel.userProperty.observe(viewLifecycleOwner, Observer {
            et_reservation_detail_user_name.setText(it.userName)
            et_reservation_detail_user_email.setText(it.email)
            et_reservation_detail_user_phone.setText(it.phoneNumber)
        })

        reservationViewModel.reservationInfo.observe(viewLifecycleOwner, Observer{

            res_hotel_name.text = it.hotel?.name
            res_hotel_addr.text = it.hotel?.address

            tv_enter_date_time_check.text = it.checkInDateTime
            tv_exit_date_time_check.text = it.checkOutDateTime
        })

        fab_reservation_detail_info_selected_ok.setOnClickListener {
            //val msg = getDeliverMsg()
            // TODO: 7/25/2020 API 로 메시지 전달하기  pet 수 만큼 해야 함.
            findNavController().navigate(R.id.action_reservationDetailFragment2_to_reservationDoneFragment2)
        }
    }

    private fun getDeliverMsg(): String {
        val text = et_reservation_detail_user_msg.text
        if(!text.isNullOrEmpty() &&
            text.toString().isNotEmpty()){
            return text.toString()
        }
        return ""
    }


}
