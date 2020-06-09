package com.prography.pethotel.ui.places

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.prography.pethotel.R
import com.prography.pethotel.models.Hotel
import kotlinx.android.synthetic.main.place_info_fragment.*

class PlaceInfoFragment : Fragment() {

    private lateinit var viewModel: PlaceInfoViewModel
    private var hotelList : ArrayList<Hotel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.place_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PlaceInfoViewModel::class.java)

        //recycler view 만들기

        //filtering 하는 기능 제공하기.
        //1. near by places
        //2.monitor providers
        //3. price discounts (?)

        makeDummyHotelInfo()

        val placeInfoAdapter = PlaceListAdapter(requireContext(), hotelList)
        rv_place_info.apply {
            adapter = placeInfoAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        val popularPlaceListAdapter = PopularPlaceListAdapter(requireContext(), hotelList)
        rv_popular_place_info.apply {
            adapter = popularPlaceListAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    private fun makeDummyHotelInfo(){
        if(hotelList.isNotEmpty()){
            return
        }else{
            for(x in 0 .. 20){
                hotelList.add(
                    Hotel(x, "", "", "애견호텔 $x",
                        "This is a dummy description #$x", "서울시 용산구 $x 길 $x ",
                        "Dummy address detail #$x", "dummy zip code #$x",
                        "30303040", "234234234", "09:00",
                        "20:00", "09:00", "18:00",
                        "09:00", "18:00", 10000, 20000, 20000,
                        "010-1123-1231", true, true, 3, "www.dummylink.com")
                )
            }
        }
    }

}
