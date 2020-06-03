package com.prography.pethotel.ui.reservation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.prography.pethotel.R
import kotlinx.android.synthetic.main.fragment_reservation.*

class ReservationFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservation, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fab_reservation_basic_info_selected_ok.setOnClickListener {
            findNavController().navigate(R.id.action_reservationFragment_to_reservationDetailFragment)
        }
    }


}
