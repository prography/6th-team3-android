package com.prography.pethotel.ui.places

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.getbase.floatingactionbutton.FloatingActionsMenu.OnFloatingActionsMenuUpdateListener
import com.prography.pethotel.R
import com.prography.pethotel.api.main.MainApi
import com.prography.pethotel.api.main.response.GetReviewResponse
import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.api.main.response.HotelPrice
import com.prography.pethotel.api.main.response.HotelReviewData
import com.prography.pethotel.room.AppDatabase
import com.prography.pethotel.room.auth.AccountPropViewModelFactory
import com.prography.pethotel.room.auth.AccountPropertiesViewModel
import com.prography.pethotel.ui.places.adapters.PriceAdapter
import com.prography.pethotel.ui.places.adapters.ReviewAdapter
import com.prography.pethotel.utils.AuthTokenViewModel
import com.prography.pethotel.utils.AuthTokenViewModelFactory
import kotlinx.android.synthetic.main.floating_menu.*
import kotlinx.android.synthetic.main.fragment_place_info_details_v2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "PlaceInfoDetailsFragmen"

class PlaceInfoDetailsFragment : Fragment() {


    private var phoneNumber : String? = ""
    private var hotelWebsite : String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_info_details_v2, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val database = AppDatabase.getInstance(requireContext())
        val accountDao = database.accountPropertiesDao


        val hotel : HotelData = arguments?.get("hotel") as HotelData
        phoneNumber = hotel.phoneNumber
        hotelWebsite = hotel.pageLink

        var reviews: ArrayList<HotelReviewData>

        val call = MainApi.petHotelRetrofitService.getHotelReviews(hotel.id)
        val callback = object : Callback<GetReviewResponse>{
            override fun onFailure(call: Call<GetReviewResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                no_review_layout.visibility = View.VISIBLE
                user_average_rating.rating = 0.toFloat()
            }
            override fun onResponse(
                call: Call<GetReviewResponse>,
                response: Response<GetReviewResponse>
            ) {
                Log.d(TAG, "onResponse: ${response.body()}")
                val response = response.body()

                if (response != null) {
                    if(response.data.isNullOrEmpty()){
                        no_review_layout.visibility = View.VISIBLE
                        user_average_rating.rating = 0.toFloat()
                    }else{
                        reviews = response.data as ArrayList<HotelReviewData>

                        var ratingSum = 0
                        var ratingAvg: Int
                        reviews.forEach {
                            ratingSum += it.rating
                        }
                        ratingAvg = ratingSum / response.data.size
                        user_average_rating.rating = ratingAvg.toFloat()

                        val reviewAdapter = ReviewAdapter(
                            reviews = reviews,
                            context = requireContext()
                        )
                        detail_rv_review.apply {
                            adapter = reviewAdapter
                            setHasFixedSize(true)
                            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        }
                    }
                }
            }
        }
        call.enqueue(callback)

        /* FAB 초기화 하기 (누르면 여러개 나오는 FAB)*/
        initFloatingActionButtonMenu()

        /*뷰에 호텔 정보 넣기 */
        setHotelDataToView(hotel)

        /*가격, 시간, 무게 관련 정보 리스트에 띄우기 */
        val priceList = hotel.prices ?: emptyList()
        Log.d(TAG, "onActivityCreated: $priceList")
        if(!priceList.isNullOrEmpty()){
            val priceAdapter = PriceAdapter(requireContext(), priceList as ArrayList<HotelPrice>)
            rv_price.apply {
                adapter = priceAdapter
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }else{
            no_price_layout.visibility = View.VISIBLE
        }

        /*호텔 정보를 예약 화면으로 넘긴다 */
        action_reserve_now.setOnClickListener {
            val bundle = bundleOf("hotel" to hotel)
            findNavController().navigate(R.id.action_placeInfoDetailsFragment_to_reservationFragment2, bundle)
            multiple_actions_menu.collapse()
        }

        action_write_review.setOnClickListener {
            val bundle = bundleOf("hotel" to hotel)
            findNavController().navigate(R.id.action_placeInfoDetailsFragment_to_writeReviewFragment, bundle)
            multiple_actions_menu.collapse()
        }
    }


    @SuppressLint("SetTextI18n")
    private fun setHotelDataToView(hotel : HotelData){

        place_detail_name.text = hotel.name

        when {
            hotel.address == null || hotel.addressDetail == null -> {
                place_detail_address.text = "주소정보없음"
            }
            else -> {
                place_detail_address.text = hotel.address + "\n" + hotel.addressDetail
            }
        }

        /* 설명이 다섯 글자 미만이면 데이터가 올바르지 않은 것으로 처리한다. */
        if(hotel.description.isEmpty() || hotel.description.length < 5){
            tv_place_detail.text = "설명이 없습니다!"
        }else{
            tv_place_detail.text = hotel.description
        }

        when {
            hotel.monitorAvailable -> {
                tv_monitoring_contents.append("제공")
            }
            else -> {
                tv_monitoring_contents.append("미제공")
            }
        }

        when{
            hotel.isNeuteredOnly -> {
                tv_neuter_needed.append("필요")
            }else -> {
            tv_neuter_needed.append("필요없음")
        }
        }
        week_op_time.text = "${hotel.weekOpenTime ?: "변동가능"}\n~\n${hotel.weekCloseTime ?: "변동가능"}"
        sat_op_time.text = "${hotel.satOpenTime ?: "변동가능"}\n~\n${hotel.satCloseTime ?: "변동가능"}"
        sun_op_time.text = "${hotel.sunOpenTime ?: "변동가능"}\n~\n${hotel.sunCloseTime ?: "변동가능"}"
    }

    private fun initFloatingActionButtonMenu() {
        val floatingActionsMenu = multiple_actions_menu

        action_check_website.setOnClickListener {
            startOpenWebsiteIntent(hotelWebsite)
            floatingActionsMenu.collapse()
        }

        action_make_phone_call.setOnClickListener {
            startPhoneIntent(phoneNumber)
            floatingActionsMenu.collapse()
        }

        action_save_like.setOnClickListener { floatingActionsMenu.collapse() }


        floating_menu_background.setOnClickListener { floatingActionsMenu.collapse() }

        floatingActionsMenu.setOnFloatingActionsMenuUpdateListener(object :
            OnFloatingActionsMenuUpdateListener {
            override fun onMenuExpanded() {
                floating_menu_background.visibility = View.VISIBLE
            }

            override fun onMenuCollapsed() {
                floating_menu_background.visibility = View.GONE
            }
        })
    }

    private fun startOpenWebsiteIntent(hotelWebsite: String? = "") {
        if(URLUtil.isValidUrl(hotelWebsite)){
            val openWebsiteIntent = Intent(Intent.ACTION_VIEW)
            openWebsiteIntent.data = Uri.parse(hotelWebsite)
            startActivity(openWebsiteIntent)
        }else{
            Toast.makeText(requireContext(), "웹사이트가 없습니다!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startPhoneIntent(phoneNumber: String? = "") {
        if(phoneNumber.isNullOrEmpty()){
            Toast.makeText(requireContext(), "전화번호가 없습니다!", Toast.LENGTH_SHORT).show()
            return
        }
        val isValid = Patterns.PHONE.matcher(phoneNumber).matches()
        if(!isValid){
            Toast.makeText(requireContext(), "전화번호가 유효하지 않습니다!", Toast.LENGTH_SHORT).show()
            return
        }
        if(!isCallPermissionGranted()){
            requestCallPermission()
        }else{
            val callIntent = Intent(Intent.ACTION_DIAL,
                Uri.fromParts("tel", phoneNumber, null))
            startActivity(callIntent)
        }
    }

    private fun isCallPermissionGranted() : Boolean{
        return ActivityCompat.checkSelfPermission(requireContext(),
            Manifest.permission.CALL_PHONE) ==
                PackageManager.PERMISSION_GRANTED
    }

    private fun requestCallPermission(){
        ActivityCompat.requestPermissions(requireActivity(),
        arrayOf(Manifest.permission.CALL_PHONE), 100)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 100 &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startPhoneIntent(phoneNumber)
            }
        }
    }

