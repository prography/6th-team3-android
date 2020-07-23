package com.prography.pethotel.ui.places

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.prography.pethotel.R
import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.room.main.MainDbViewModel
import com.prography.pethotel.ui.places.adapters.PopularPlaceAdapter
import com.prography.pethotel.utils.DummyData
import kotlinx.android.synthetic.main.fragment_popular_place.*

@Suppress("DEPRECATION")
class PopularPlaceFragment : Fragment() {


    private lateinit var placeInfoViewModel: PlaceInfoViewModel
    private val mainDbViewModel : MainDbViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //모든 Fragment 들이 하나의 place info data source 를 공유하면서 display 할 때
        //filter 만 다르게 한다.
        placeInfoViewModel = requireActivity().let {
            ViewModelProviders.of(it).get(PlaceInfoViewModel::class.java)
        }


        placeInfoViewModel.hotelList.observe(viewLifecycleOwner, Observer {list ->
            val subList = ArrayList(list)
            //TODO rating 으로 필터링 해야하는데, 호텔 객체에 rate 필드가 없음
            initList(hotelList = subList)

        })
    }

    private fun initList(hotelList : ArrayList<HotelData>){
        val popularPlaceListAdapter = PopularPlaceAdapter(requireContext(), hotelList)
        rv_popular_list.apply {
            adapter = popularPlaceListAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}