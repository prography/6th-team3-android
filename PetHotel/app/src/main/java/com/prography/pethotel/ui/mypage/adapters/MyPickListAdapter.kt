package com.prography.pethotel.ui.mypage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prography.pethotel.R
import com.prography.pethotel.api.main.response.HotelData
import kotlinx.android.synthetic.main.place_info_view_holder.view.*


class MyPickListAdapter (
    val context: Context,
    private val hotelList : ArrayList<HotelData>
    ) : ListAdapter<HotelData, MyPickListAdapter.MyPickListViewHolder>(
    HotelDiffUtilCallback()
){


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPickListViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.place_info_view_holder, parent, false)

            return MyPickListViewHolder(
                view
            )
        }

        override fun getItemCount(): Int {
            return hotelList.size
        }

    override fun onBindViewHolder(holder: MyPickListViewHolder, position: Int) {
            val hotel = hotelList[position]
            holder.bind(hotel)

            holder.itemView.setOnClickListener {
                val bundle = bundleOf("hotel" to hotel)
                it.findNavController().navigate(R.id.action_myPageFragment_to_myPickFragment, bundle)
            }
        }

        class MyPickListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(hotel : HotelData){
                itemView.place_info_name.text = hotel.name
                itemView.place_info_address.text = hotel.address
                //distance 는 안함
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
}

