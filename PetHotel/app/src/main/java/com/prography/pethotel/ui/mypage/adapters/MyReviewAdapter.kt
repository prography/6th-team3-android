package com.prography.pethotel.ui.mypage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prography.pethotel.R
import com.prography.pethotel.api.main.response.HotelReviewData
import kotlinx.android.synthetic.main.hotel_review_view_holder.view.*

class MyReviewAdapter(
    private val context: Context,
    private val hotelReviewList : ArrayList<HotelReviewData>
) : ListAdapter<HotelReviewData, MyReviewAdapter.MyReviewViewHolder>(
    MyReviewDiffUtilCallback()
){


    override fun getItemCount(): Int {
        return hotelReviewList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.hotel_review_view_holder, parent, false)
        return MyReviewViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        holder.bind(hotelReviewList[position])
    }

    class MyReviewDiffUtilCallback : DiffUtil.ItemCallback<HotelReviewData>() {
        override fun areItemsTheSame(oldItem: HotelReviewData, newItem: HotelReviewData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HotelReviewData, newItem: HotelReviewData): Boolean {
            return oldItem.content == newItem.content
        }

    }


    class MyReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hotelReview : HotelReviewData){

            //TODO 호텔 이름이랑 유저 이름 가져오기
//            itemView.tv_review_hotel_name.text =

//            itemView.tv_review_username.text =

            itemView.tv_review_content.text = hotelReview.content

        }
    }
}