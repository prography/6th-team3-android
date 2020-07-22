package com.prography.pethotel.ui.places

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.prography.pethotel.R
import com.prography.pethotel.api.main.HotelsApiService
import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.ui.places.adapters.DiscountPlaceAdapter
import com.prography.pethotel.utils.DummyData
import kotlinx.android.synthetic.main.fragment_discount_place.*


private const val TAG = "DiscountPlaceFragment"
@Suppress("DEPRECATION")
class DiscountPlaceFragment : Fragment() {

    private lateinit var placeInfoViewModel: PlaceInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discount_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        placeInfoViewModel = requireActivity().let {
            ViewModelProviders.of(it).get(PlaceInfoViewModel::class.java)
        }

        placeInfoViewModel.hotelList.observe(viewLifecycleOwner, Observer {list ->
            var subList = ArrayList(list)

            subList = subList.filter {
                !it.prices.isNullOrEmpty()
            } as ArrayList<HotelData>

            subList.sortWith(
                compareBy { it.prices[0].price }
            )
            initList(subList)
        })
    }

    private fun initList(hotelList : ArrayList<HotelData>){
        val discountPlaceAdapter = DiscountPlaceAdapter(requireContext(), hotelList)
        rv_discount_list.apply {
            adapter = discountPlaceAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
        discountPlaceAdapter.notifyDataSetChanged()
    }


    companion object {
         @JvmStatic
        fun newInstance() =
          DiscountPlaceFragment()
    }
}