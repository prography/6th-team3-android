package com.prography.pethotel.ui.reservation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.prography.pethotel.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_reservation_detail.*


class ReservationDetailFragment : Fragment() {

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

        fab_reservation_detail_info_selected_ok.setOnClickListener {
            //예약 정보를 전달하고 완료 화면으로 이동한다.
            findNavController().navigate(R.id.action_reservationDetailFragment_to_reservationDoneFragment)
        }
    }

}
