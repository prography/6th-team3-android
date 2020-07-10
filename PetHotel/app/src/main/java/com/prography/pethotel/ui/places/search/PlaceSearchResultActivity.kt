package com.prography.pethotel.ui.places.search

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prography.pethotel.R
import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.ui.places.util.GenericRecyclerViewAdapter
import com.prography.pethotel.utils.DummyData
import kotlinx.android.synthetic.main.activity_place_search_result.*
import kotlinx.android.synthetic.main.place_info_view_holder.view.*



class PlaceSearchResultActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_search_result)

        val query = intent.getStringExtra("QUERY")


        //TODO change the list with search result Array List
        val list = DummyData.hotelDummyList
        val myAdapter = object : GenericRecyclerViewAdapter<HotelData>(list){
            override fun getLayoutId(position: Int, obj: HotelData): Int {
                return when(obj){
                    else -> {
                        R.layout.place_info_view_holder
                    }
                }
            }

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                return PlaceInfoViewHolder(view)
            }
        }

        rv_search_result_activity.apply {
            layoutManager = LinearLayoutManager(this@PlaceSearchResultActivity)
            setHasFixedSize(true)
            adapter = myAdapter
        }

    }


    class PlaceInfoViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), GenericRecyclerViewAdapter.Binder<HotelData>{

        override fun bind(data: HotelData) {
            itemView.place_info_address.text = data.address
            itemView.place_info_name.text = data.name

            itemView.setOnClickListener {
                val bundle = bundleOf("hotel" to data)
                it.findNavController().navigate(R.id.action_placeInfoFragment_to_placeInfoDetailsFragment, bundle)
            }
//TODO itemView.place_info_image -> set Image
//TODO itemView.place_info_distance.text = getDistanceFromCurrentLocation()
        }
    }
}