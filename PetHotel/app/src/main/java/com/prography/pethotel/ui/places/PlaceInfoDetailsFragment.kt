package com.prography.pethotel.ui.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.getbase.floatingactionbutton.FloatingActionsMenu
import com.getbase.floatingactionbutton.FloatingActionsMenu.OnFloatingActionsMenuUpdateListener
import com.prography.pethotel.R
import com.prography.pethotel.models.Hotel
import kotlinx.android.synthetic.main.floating_menu.*
import kotlinx.android.synthetic.main.place_info_detail_descriptions_layout.*
import kotlinx.android.synthetic.main.place_info_detail_time_etc_layout.*
import kotlinx.android.synthetic.main.fragment_place_info_details.*



class PlaceInfoDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_info_details, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        initFloatingActionButtonMenu()

        val hotel : Hotel = arguments?.get("hotel") as Hotel

        place_detail_name.text = hotel.hotelName
        place_detail_address.text = hotel.addressDetail
//        btn_place_detail_info.setOnClickListener {
//
//        }
//        btn_place_detail_like.setOnClickListener {
//            Toast.makeText(requireContext(), "저장되었습니다!", Toast.LENGTH_SHORT).show()
//        }
//        btn_place_detail_phone.setOnClickListener {
//            Toast.makeText(requireContext(), hotel.phoneNumber, Toast.LENGTH_SHORT).show()
//        }
        tv_place_detail.text = hotel.description

        val weekTime = "${hotel.weekOpenTime} ~ ${hotel.weekCloseTime}"
        val satTime = "${hotel.satOpenTime} ~ ${hotel.satCloseTime}"
        val sunTime = "${hotel.sunOpenTime} ~ ${hotel.sunCloseTime}"
        title_week_time.append(weekTime)
        title_sat_time.append(satTime)
        title_sun_time.append(sunTime)

        when {
            hotel.monitorAvailable -> {
                tv_monitoring_contents.text = "제공"
            }
            else -> {
                tv_monitoring_contents.text = "미제공"
            }
        }

        when{
            hotel.isNeuteredOnly -> {
                tv_neuter_needed.text = "중성화 필요"
            }else -> {
                tv_neuter_needed.text = "중성화 필요 없음"
            }
        }

        tv_week_price.append("${hotel.weekPrice} 원")
        tv_sat_price.append("${hotel.satPrice} 원")
        tv_sun_price.append("${hotel.sunPrice} 원")
    }


    private fun initFloatingActionButtonMenu() {
        val floatingActionsMenu = multiple_actions_menu

        action_check_website.setOnClickListener {
            floatingActionsMenu.collapse()
        }

        action_make_phone_call.setOnClickListener { floatingActionsMenu.collapse() }

        action_reserve_now.setOnClickListener { floatingActionsMenu.collapse() }

        action_save_like.setOnClickListener { floatingActionsMenu.collapse() }

        floating_menu_background.setOnClickListener { floatingActionsMenu.collapse() }
        floatingActionsMenu.setOnFloatingActionsMenuUpdateListener(object :
            OnFloatingActionsMenuUpdateListener {
            override fun onMenuExpanded() {
                floating_menu_background.visibility = View.VISIBLE
            }

            override fun onMenuCollapsed() {
                floating_menu_background.visibility = View.GONE
            }
        })
    }

}
