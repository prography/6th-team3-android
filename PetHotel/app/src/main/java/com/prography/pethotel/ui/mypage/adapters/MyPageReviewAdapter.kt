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
import kotlinx.android.synthetic.main.my_page_review_view_holder.view.*
import kotlinx.android.synthetic.main.place_detail_review_view_holder.view.*
import kotlinx.android.synthetic.main.place_detail_review_view_holder.view.review_created_at
import kotlinx.android.synthetic.main.place_detail_review_view_holder.view.tv_user_review
import kotlinx.android.synthetic.main.place_detail_review_view_holder.view.user_rating

class MyPageReviewAdapter(
    private val context: Context,
    private val hotelReviewList : ArrayList<HotelReviewData>
) : ListAdapter<HotelReviewData, MyPageReviewAdapter.MyReviewViewHolder>(
    MyReviewDiffUtilCallback()
){

    override fun getItemCount(): Int {
        return hotelReviewList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.my_page_review_view_holder, parent, false)
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

        fun bind(hotelReviewData : HotelReviewData){
            itemView.tv_user_review.text = hotelReviewData.content
            itemView.user_rating.rating = hotelReviewData.rating.toFloat()

            val date = hotelReviewData.createdAt.split("T")[0]
            itemView.review_created_at.text = date
            itemView.review_hotel_name.text = hotelReviewData.hotelName
        }
    }
}