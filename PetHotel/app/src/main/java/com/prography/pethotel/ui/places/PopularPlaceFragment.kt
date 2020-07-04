package com.prography.pethotel.ui.places

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.prography.pethotel.R
import com.prography.pethotel.models.Hotel
import com.prography.pethotel.ui.places.adapters.PopularPlaceAdapter
import com.prography.pethotel.utils.DummyData
import kotlinx.android.synthetic.main.fragment_popular_place.*

@Suppress("DEPRECATION")
class PopularPlaceFragment : Fragment() {


    private lateinit var placeInfoViewModel: PlaceInfoViewModel

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

        placeInfoViewModel.hotelList.observe(viewLifecycleOwner, Observer {
            val filteredArray = filterBy(FilterType.POPULARITY, it)
            initList(hotelList = filteredArray)
        })

    }

    private fun initList(hotelList : ArrayList<Hotel>){
        //TODO change this part with real hotel data
        val popularPlaceListAdapter = PopularPlaceAdapter(requireContext(), DummyData.hotelDummyList)
        rv_popular_list.apply {
            adapter = popularPlaceListAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    companion object {
           @JvmStatic
        fun newInstance() =
            PopularPlaceFragment()
    }
}