package com.prography.pethotel.ui.places

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prography.pethotel.R
import com.prography.pethotel.models.Hotel
import kotlinx.android.synthetic.main.place_info_view_holder.view.*

class PlaceListAdapter(
    val context: Context,
    private val hotelList : ArrayList<Hotel>
) : ListAdapter<Hotel, PlaceListAdapter.PlaceListViewHolder>(HotelDiffUtilCallback()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.place_info_view_holder, parent, false)

        return PlaceListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hotelList.size
    }

    override fun onBindViewHolder(holder: PlaceListViewHolder, position: Int) {
        val hotel = hotelList[position]
        holder.bind(hotel)

        holder.itemView.setOnClickListener {
            val bundle = bundleOf("hotel" to hotel)
            it.findNavController().navigate(R.id.action_placeInfoFragment_to_placeInfoDetailsFragment, bundle)
        }
    }

    class PlaceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hotel : Hotel){
            itemView.place_info_name.text = hotel.hotelName
            itemView.place_info_address.text = hotel.address
            //distance 는 안함
        }
    }

    class HotelDiffUtilCallback : DiffUtil.ItemCallback<Hotel>() {
        override fun areContentsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
            return oldItem.hotelId == newItem.hotelId
        }

        override fun areItemsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
            return oldItem == newItem
        }
    }
}