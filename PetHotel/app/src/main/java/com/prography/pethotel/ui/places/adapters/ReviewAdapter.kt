package com.prography.pethotel.ui.places.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prography.pethotel.R
import com.prography.pethotel.api.main.response.HotelReviewData
import kotlinx.android.synthetic.main.place_detail_review_view_holder.view.*

class ReviewAdapter(
    private val reviews : ArrayList<HotelReviewData>,
    val context : Context
)
    : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {


    class ReviewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(hotelReviewData : HotelReviewData){
            itemView.tv_user_review.text = hotelReviewData.content
            itemView.user_rating.rating = hotelReviewData.rating.toFloat()

            val date = hotelReviewData.createdAt.split("T")[0]
            itemView.review_created_at.text = date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.place_detail_review_view_holder, parent, false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(hotelReviewData = reviews[position])
    }
}