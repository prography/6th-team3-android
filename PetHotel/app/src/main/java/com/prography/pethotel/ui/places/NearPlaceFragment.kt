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
import com.prography.pethotel.ui.places.adapters.NearPlaceAdapter
import com.prography.pethotel.ui.places.adapters.PopularPlaceAdapter
import com.prography.pethotel.utils.DummyData
import kotlinx.android.synthetic.main.fragment_near_place.*
import kotlinx.android.synthetic.main.fragment_popular_place.*


@Suppress("DEPRECATION")
class NearPlaceFragment : Fragment() {

    private lateinit var placeInfoViewModel: PlaceInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_near_place, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        placeInfoViewModel = requireActivity().let {
            ViewModelProviders.of(it).get(PlaceInfoViewModel::class.java)
        }

        placeInfoViewModel.hotelList.observe(viewLifecycleOwner, Observer {
            val filteredArray = filterBy(FilterType.DISTANCE, it)
            initList(hotelList = filteredArray)
        })

        val nearPlaceAdapter = NearPlaceAdapter(requireContext(), DummyData.hotelDummyList)
        rv_near_list.apply {
            adapter = nearPlaceAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initList(hotelList : ArrayList<Hotel>){
        //TODO change this part with real hotel data
        val nearPlaceAdapter = NearPlaceAdapter(requireContext(), DummyData.hotelDummyList)
        rv_near_list.apply {
            adapter = nearPlaceAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    companion object {
            @JvmStatic
        fun newInstance() =
            NearPlaceFragment()
    }
}