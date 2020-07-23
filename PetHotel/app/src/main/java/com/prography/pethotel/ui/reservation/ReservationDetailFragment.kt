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

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val view = inflater.inflate(R.layout.fragment_reservation_detail, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        /*1. 기존 회원 정보 사용을 클릭하면 회원 이름, 이메일, 전화번호를 불러온다. (어차피 달라지는건 없지만 ...)
        *    만약 이름을 수정하거나 한다면 update user profile api 가 있어야 하는데, 그게 아직 없음
        * 2. 메시지 작성하면 post 형식에 맞춰서 호텔 아이디, 펫 아이디, 유저아이디랑 같이 API 콜 날리기
        * 3. 예약 불러오기가... User 별로 불러오는게 맞는지 확인해야 함.
        * 4. 체크인, 체크아웃 날짜 예약 할때 어디다가...?
        * */


        fab_reservation_detail_info_selected_ok.setOnClickListener {
             findNavController().navigate(R.id.action_reservationDetailFragment2_to_reservationDoneFragment2)
        }
    }

}
