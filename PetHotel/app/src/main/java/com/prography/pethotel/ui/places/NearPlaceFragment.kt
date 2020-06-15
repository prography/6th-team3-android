package com.prography.pethotel.ui.places

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.prography.pethotel.R
import com.prography.pethotel.ui.places.adapters.NearPlaceAdapter
import com.prography.pethotel.utils.DummyData
import kotlinx.android.synthetic.main.fragment_near_place.*


class NearPlaceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_near_place, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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