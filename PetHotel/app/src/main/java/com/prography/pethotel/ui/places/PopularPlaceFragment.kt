package com.prography.pethotel.ui.places

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.prography.pethotel.R
import com.prography.pethotel.ui.places.adapters.PopularPlaceAdapter
import com.prography.pethotel.utils.DummyData
import kotlinx.android.synthetic.main.fragment_popular_place.*

class PopularPlaceFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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