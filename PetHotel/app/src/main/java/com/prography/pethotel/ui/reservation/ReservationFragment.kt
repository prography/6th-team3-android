package com.prography.pethotel.ui.reservation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.prography.pethotel.R
import kotlinx.android.synthetic.main.fragment_reservation.*
import kotlinx.android.synthetic.main.reservation_date_time_layout.*

class ReservationFragment : Fragment() {

    private lateinit var dateTimeViewModel: DateTimeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservation, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        dateTimeViewModel = ViewModelProviders.of(requireActivity()).get(DateTimeViewModel::class.java)

        if(dateTimeViewModel.dateTime.value == null){
            dateTimeViewModel.initDateTime()
        }

        setClickListeners()
        observeDateTime()

        fab_reservation_basic_info_selected_ok.setOnClickListener {
            findNavController().navigate(R.id.action_reservationFragment_to_reservationDetailFragment)
        }

    }

    private fun setClickListeners(){
        tv_hotel_enter_date.setOnClickListener{
            //날짜 선택 다일로그 띄우기,
            //선택된 날짜로 텍스트 업데이트 하기
            val enterDatePicker = DatePickerFragment(dateTimeViewModel, true)
            enterDatePicker.show(parentFragmentManager, "datePicker")
        }
        tv_hotel_exit_date.setOnClickListener {
            val exitDatePicker = DatePickerFragment(dateTimeViewModel, false)
            exitDatePicker.show(parentFragmentManager, "datePicker")
        }
        tv_hotel_enter_time.setOnClickListener {
            val enterTimePicker = TimePickerFragment(dateTimeViewModel, true)
            enterTimePicker.show(parentFragmentManager, "timePicker")
        }
        tv_hotel_exit_time.setOnClickListener {
            val exitTimePicker = TimePickerFragment(dateTimeViewModel, false)
            exitTimePicker.show(parentFragmentManager, "timePicker")
        }
    }


    private fun observeDateTime(){
        dateTimeViewModel.dateTime.observe(requireActivity(), Observer {
            Log.d("VIEWMODEL", "observing ... ")
            tv_hotel_enter_date.text = it.enterDate
            tv_hotel_exit_date.text = it.exitDate
            tv_hotel_enter_time.text = it.enterTime
            tv_hotel_exit_time.text = it.exitTime
        })
    }



}
