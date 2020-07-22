package com.prography.pethotel.ui.places

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.prography.pethotel.R
import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.ui.places.adapters.NearPlaceAdapter
import kotlinx.android.synthetic.main.fragment_near_place.*


private const val TAG = "NearPlaceFragment"

@Suppress("DEPRECATION")
class NearPlaceFragment : Fragment() {

    private lateinit var placeInfoViewModel: PlaceInfoViewModel
    private lateinit var nearPlaceAdapter : NearPlaceAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate From Fragment Near")

        placeInfoViewModel = ViewModelProvider(requireActivity())[PlaceInfoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ")
        return inflater.inflate(R.layout.fragment_near_place, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.d(TAG, "onActivityCreated: ")


//        placeInfoViewModel.getDistanceFilteredList().observe(viewLifecycleOwner, Observer {
//            initList(hotelList = it)
//            nearPlaceAdapter.notifyDataSetChanged()
//        })



        placeInfoViewModel.hotelList.observe(viewLifecycleOwner, Observer { list ->
            val subList = ArrayList(list)

            subList.sortWith(
                compareBy<HotelData>{it.distanceFromUser}
                .thenBy { it.name }
            )
            initList(subList)
        })
//
//        placeInfoViewModel.hotelList.value?.forEach {
//            Log.d(TAG, "onActivityCreated: getHotelImages(it)")
//            placeInfoViewModel.getHotelImages(it)
//        }

        placeInfoViewModel.hotelImages.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onActivityCreated: Observing Images ")
            nearPlaceAdapter.notifyDataSetChanged()
        })
    }

    private fun initList(hotelList : ArrayList<HotelData>){
        nearPlaceAdapter = NearPlaceAdapter(requireContext(), hotelList)
        rv_near_list.apply {
            adapter = nearPlaceAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }


    companion object {
            @JvmStatic
        fun newInstance() =
            NearPlaceFragment()
    }
}