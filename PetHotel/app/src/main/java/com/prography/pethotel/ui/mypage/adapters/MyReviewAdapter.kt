package com.prography.pethotel.ui.mypage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prography.pethotel.R
import com.prography.pethotel.models.HotelReview
import kotlinx.android.synthetic.main.hotel_review_view_holder.view.*

class MyReviewAdapter(
    private val context: Context,
    private val hotelReviewList : ArrayList<HotelReview>
) : ListAdapter<HotelReview, MyReviewAdapter.MyReviewViewHolder>(
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

    class MyReviewDiffUtilCallback : DiffUtil.ItemCallback<HotelReview>() {
        override fun areItemsTheSame(oldItem: HotelReview, newItem: HotelReview): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HotelReview, newItem: HotelReview): Boolean {
            return oldItem.reviewContent == newItem.reviewContent
        }

    }


    class MyReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hotelReview : HotelReview){
            itemView.tv_review_hotel_name.text = hotelReview.hotelName
//            itemView.tv_review_username.text = hotelReview.userName
            itemView.tv_review_content.text = hotelReview.reviewContent

        }
    }
}