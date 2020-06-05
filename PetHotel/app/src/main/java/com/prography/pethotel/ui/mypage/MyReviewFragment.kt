package com.prography.pethotel.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.prography.pethotel.R
import com.prography.pethotel.models.HotelReview
import kotlinx.android.synthetic.main.my_page_review_fragment.*

class MyReviewFragment : Fragment(){

    var hotelReviews : ArrayList<HotelReview> = ArrayList()
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


        makeDummyReviews()
        myReviewAdapter = MyReviewAdapter(requireContext(), hotelReviews)

        rv_my_review.apply {
            adapter = myReviewAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun makeDummyReviews(){
        if(hotelReviews.isNotEmpty()){
            return
        }else{
            for(x in 1 .. 5){
                hotelReviews.add(
                    HotelReview(
                        x,
                        "2020-05-05",
                        "2020-05-05",
                        "${x}번 털실맘",
                        "아무 호텔 넘버 $x",
                        "This is a sample review. I will be changing this " +
                                "part with real data in $x days.",
                        5
                    )
                )
            }
        }

    }
}