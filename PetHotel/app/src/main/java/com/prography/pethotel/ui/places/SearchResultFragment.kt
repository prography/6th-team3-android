package com.prography.pethotel.ui.places

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prography.pethotel.R
import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.ui.places.util.GenericRecyclerViewAdapter
import com.prography.pethotel.utils.DummyData
import kotlinx.android.synthetic.main.fragment_search_result.*
import kotlinx.android.synthetic.main.place_info_view_holder.view.*


class SearchResultFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val list = DummyData.hotelDummyList

        val myAdapter = object : GenericRecyclerViewAdapter<HotelData>(list){
            override fun getLayoutId(position: Int, obj: HotelData): Int {
                return when(obj){
                    is HotelData -> R.layout.place_info_view_holder
                    else -> R.layout.no_search_result_layout
                }
            }

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                return ViewHolderFactory.create(view, viewType)
            }
        }

        rv_search_result.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = myAdapter
        }
    }
}


object ViewHolderFactory{

    fun create(view : View, viewType : Int) : RecyclerView.ViewHolder{
        return when(viewType){
            R.layout.place_info_view_holder -> PlaceInfoViewHolder(view)
            R.layout.no_search_result_layout -> NoResultViewHolder(view)
            else -> {
                PlaceInfoViewHolder(view)
            }
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

    class NoResultViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), GenericRecyclerViewAdapter.Binder<Any>{

        override fun bind(data: Any) {

        }

    }
}