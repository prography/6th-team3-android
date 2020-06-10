package com.prography.pethotel.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.prography.pethotel.R
import com.prography.pethotel.models.Hotel
import kotlinx.android.synthetic.main.my_page_my_pick_fragment.*

class MyPickFragment : Fragment() {

    private lateinit var viewModel: MyPickViewModel
    private var hotelList : ArrayList<Hotel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_page_my_pick_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MyPickViewModel::class.java)

        makeDummyHotelInfo()

        val myPickListAdapter = MyPickListAdapter(requireContext(), hotelList)
        rv_my_pick.apply {
            adapter = myPickListAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    private fun makeDummyHotelInfo(){
        if(hotelList.isNotEmpty()){
            return
        }else{
            for(x in 0 .. 5){
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

