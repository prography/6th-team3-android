package com.prography.pethotel.ui.reservation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.prography.pethotel.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_reservation_detail.*
import kotlinx.android.synthetic.main.reservation_detail_check_hotel_layout.*


class ReservationDetailFragment : Fragment() {

    lateinit var dateTimeViewModel: DateTimeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reservation_detail, container, false)
        //TODO custom toolbar 만들기

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //date time view model 에서 정보 받아와서 예약 정보 확인 화면에 업데이트 하기
        dateTimeViewModel = ViewModelProviders.of(requireActivity()).get(DateTimeViewModel::class.java)

        val data = dateTimeViewModel.dateTime.value
        if(data != null){
            tv_enter_date_time_check.text = "${data.enterDate} - ${data.enterTime}"
            tv_exit_date_time_check.text = "${data.exitDate} - ${data.exitTime}"
        }

        fab_reservation_detail_info_selected_ok.setOnClickListener {
            //예약 정보를 전달하고 완료 화면으로 이동한다.
            findNavController().navigate(R.id.action_reservationDetailFragment_to_reservationDoneFragment)
        }
    }

}
