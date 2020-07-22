package com.prography.pethotel.ui.places

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.prography.pethotel.R
import com.prography.pethotel.api.main.MainApi
import com.prography.pethotel.api.main.response.GetReviewResponse
import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.api.main.response.HotelReviewData
import com.prography.pethotel.ui.places.adapters.ReviewAdapter
import kotlinx.android.synthetic.main.fragment_place_info_details_v2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoreReviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more_review, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        val reviewAdapter = ReviewAdapter(
//            reviews =
//            context = requireContext()
//        )
//        detail_rv_review.apply {
//            adapter = reviewAdapter
//            setHasFixedSize(true)
//            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        }
    }
}