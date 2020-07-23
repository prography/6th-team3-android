package com.prography.pethotel.ui.places.adapters


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.prography.pethotel.R
import com.prography.pethotel.api.main.response.HotelData
import kotlinx.android.synthetic.main.place_info_view_holder_v3.view.*

class PopularPlaceAdapter(
    val context: Context,
    private val hotelList : ArrayList<HotelData>
) : ListAdapter<HotelData, PopularPlaceAdapter.PopularPlaceViewHolder>(
    HotelDiffUtilCallback()
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularPlaceViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.place_info_view_holder_v3, parent, false)

        return PopularPlaceViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "${hotelList.size}")
        return hotelList.size
    }

    override fun onBindViewHolder(holder: PopularPlaceViewHolder, position: Int) {
        val hotel = hotelList[position]
        holder.bind(hotel)

        holder.itemView.setOnClickListener {
            val bundle = bundleOf("hotel" to hotel)
            it.findNavController().navigate(R.id.action_placeInfoFragment_to_placeInfoDetailsFragment, bundle)
        }

        holder.itemView.btn_place_info_like.setOnClickListener {
            //데이터베이스에 저장되어 있는지 확인 후

            //만약 저장되어 있지 않으면 insert => 모양 변경하기

            //저장되어 있으면 delete => 모양 변경하기
        }
    }

    class PopularPlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hotel : HotelData){
            itemView.place_info_name.text = hotel.name
            itemView.place_info_address.text = hotel.address

            if(hotel.distanceFromUser == Int.MAX_VALUE || hotel.distanceFromUser > 100){
                itemView.place_info_distance.text = ""
            }else{
                itemView.place_info_distance.text = "${hotel.distanceFromUser}km"
            }

            Log.d(TAG, "bind: 호텔 이미지 존재여부 ${hotel.hotelImageLinks.isNullOrEmpty()} ")

            if(!hotel.hotelImageLinks.isNullOrEmpty()){
                Glide.with(itemView.context)
                    .load(hotel.hotelImageLinks[0].link)
                    .error(R.drawable.mily_excited)
                    .transform(RoundedCorners(12))
                    .into(itemView.place_info_image)
            }
        }
    }

    class HotelDiffUtilCallback : DiffUtil.ItemCallback<HotelData>() {
        override fun areContentsTheSame(oldItem: HotelData, newItem: HotelData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: HotelData, newItem: HotelData): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val TAG = "PopularPlaceListAdapter"
    }
}
