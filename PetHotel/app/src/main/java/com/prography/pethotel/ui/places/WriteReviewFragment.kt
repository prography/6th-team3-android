package com.prography.pethotel.ui.places

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.prography.pethotel.R
import com.prography.pethotel.api.main.MainApi
import com.prography.pethotel.api.main.request.ReviewPostBody
import com.prography.pethotel.api.main.request.ReviewPostData
import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.api.main.response.HotelReviewData
import com.prography.pethotel.api.main.response.PostReviewResponse
import com.prography.pethotel.room.AppDatabase
import com.prography.pethotel.room.auth.AccountPropViewModelFactory
import com.prography.pethotel.room.auth.AccountPropertiesViewModel
import com.prography.pethotel.room.main.MainDbViewModel
import com.prography.pethotel.utils.AuthTokenViewModel
import com.prography.pethotel.utils.AuthTokenViewModelFactory
import kotlinx.android.synthetic.main.fragment_write_review.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val TAG = "WriteReviewFragment"
class WriteReviewFragment : Fragment() {


    private val authTokenViewModel: AuthTokenViewModel by activityViewModels()
    val accountPropertiesViewModel: AccountPropertiesViewModel by activityViewModels()
    val mainDbViewModel : MainDbViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_write_review, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val token = authTokenViewModel.getUserToken(requireActivity())
        accountPropertiesViewModel.fetchUser(token)

        /* 호텔 이름을 상단에 띄운다 */
        val hotel : HotelData = arguments?.get("hotel") as HotelData
        val hotelName = hotel.name

        label_review_title_detail.text = getString(R.string.hotel_review_title, hotelName)

        btn_review_upload.setOnClickListener {
            //api call
            val content = et_review_content.text.toString()
            val rating = write_review_rating_bar.rating
            val userId = authTokenViewModel.getUserId(requireActivity())

            val isContentValid = checkContentValid(content)
            if(isContentValid){
                val call = MainApi.petHotelRetrofitService.postHotelReview(
                    hotelId =  hotel.id,
                    reviewPostBody = ReviewPostBody(
                    userId = userId,
                        data = ReviewPostData(
                            content = content,
                            rating =  rating.toInt()
                        )
                    )
                )
                val callback = object : Callback<PostReviewResponse> {
                    override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                        Log.d(TAG, "onFailure: ${t.message}")
                    }

                    override fun onResponse(
                        call: Call<PostReviewResponse>,
                        response: Response<PostReviewResponse>
                    ) {
                        Log.d(TAG, "onResponse: ${response.body()}")
                        Toast.makeText(requireContext(),
                            "${accountPropertiesViewModel.userProperty.value?.userName}" +
                                    "님 소중한 후기 감사합니다!",
                            Toast.LENGTH_SHORT).show()

                        val res = response.body()
                        // TODO: 7/24/2020 데이터베이스 외래키 오류
                        if(res != null){
                            Log.d(TAG, "onResponse: inserting hotel review ")
                            mainDbViewModel.insertHotelReview(
                                HotelReviewData(
                                    id = res.data.id,
                                    userId = res.data.userId,
                                    hotelName = hotelName,
                                    content = res.data.content,
                                    rating = res.data.rating,
                                    createdAt = res.data.createdAt,
                                    updatedAt = res.data.updatedAt
                                )
                            )
                        }
                    }
                }
                call.enqueue(callback)
            }
        }
    }

    private fun checkContentValid(content: String) : Boolean{
        if(content.isEmpty()){
            return false
        }
        return true
    }
}