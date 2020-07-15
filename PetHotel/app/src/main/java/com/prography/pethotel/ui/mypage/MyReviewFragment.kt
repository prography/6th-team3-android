package com.prography.pethotel.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.prography.pethotel.R
import com.prography.pethotel.ui.mypage.adapters.MyReviewAdapter
import com.prography.pethotel.utils.DummyData
import kotlinx.android.synthetic.main.my_page_review_fragment.*

class MyReviewFragment : Fragment(){


    lateinit var myReviewAdapter : MyReviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.my_page_review_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //실제 리뷰로 바꾸기
        myReviewAdapter =
            MyReviewAdapter(
                requireContext(),
                DummyData.hotelReviews
            )

        rv_my_review.apply {
            adapter = myReviewAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


}