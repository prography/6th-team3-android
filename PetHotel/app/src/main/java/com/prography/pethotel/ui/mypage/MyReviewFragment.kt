package com.prography.pethotel.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.prography.pethotel.R
import com.prography.pethotel.api.main.response.HotelReviewData
import com.prography.pethotel.room.main.MainDbViewModel
import com.prography.pethotel.ui.mypage.adapters.MyPageReviewAdapter
import com.prography.pethotel.utils.AuthTokenViewModel
import kotlinx.android.synthetic.main.my_page_review_fragment.*

class MyReviewFragment : Fragment(){


    private lateinit var myReviewAdapter : MyPageReviewAdapter
    private val mainDbViewModel : MainDbViewModel by activityViewModels()
    private val authTokenViewModel : AuthTokenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.my_page_review_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val userId = authTokenViewModel.getUserId(requireActivity())

        mainDbViewModel.getHotelReviewByUserId(userId)

        mainDbViewModel.userReviewList.observe(viewLifecycleOwner, Observer {
            myReviewAdapter =
                MyPageReviewAdapter(
                    requireContext(),
                    it as ArrayList<HotelReviewData>
                )

            rv_my_review.apply {
                adapter = myReviewAdapter
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
            }
        })

    }

}