package com.prography.pethotel.ui.places

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.getbase.floatingactionbutton.FloatingActionsMenu.OnFloatingActionsMenuUpdateListener
import com.prography.pethotel.R
import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.models.HotelReview
import com.prography.pethotel.ui.places.util.GenericRecyclerViewAdapter
import com.prography.pethotel.utils.DummyData
import kotlinx.android.synthetic.main.floating_menu.*
import kotlinx.android.synthetic.main.place_info_detail_descriptions_layout.*
import kotlinx.android.synthetic.main.place_info_detail_time_etc_layout.*
import kotlinx.android.synthetic.main.place_detail_review_view_holder.view.*


class PlaceInfoDetailsFragment : Fragment() {


    private lateinit var phoneNumber : String
    private lateinit var hotelWebsite : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_info_details, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val hotel : HotelData = arguments?.get("hotel") as HotelData
        phoneNumber = hotel.phoneNumber
        hotelWebsite = hotel.pageLink

        initFloatingActionButtonMenu()
        setHotelDataToView(hotel)


//        val list = hotel.reviews ?: arrayListOf(EmptyReview(0))
//        val list = hotel.review ?: DummyData.hotelReviews

//        val myAdapter = object : GenericRecyclerViewAdapter<Any>(list as ArrayList<Any>){
//            override fun getLayoutId(position: Int, obj: Any): Int {
//                return when(obj){
//                    is HotelReview -> R.layout.place_detail_review_view_holder
//                    else -> R.layout.no_review_layout
//                }
//            }
//
//            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
//                return HotelViewHolderFactory.create(view, viewType)
//            }
//        }

//        rv_hotel_review.apply {
//            adapter = myAdapter
//            setHasFixedSize(true)
//            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        }
    }


    private fun setHotelDataToView(hotel : HotelData){
        place_detail_name.text = hotel.name
        place_detail_address.text = hotel.addressDetail
        tv_place_detail.text = hotel.description

        val weekTime = "${hotel.weekOpenTime} ~ ${hotel.weekCloseTime}"
        val satTime = "${hotel.satOpenTime} ~ ${hotel.satCloseTime}"
        val sunTime = "${hotel.sunOpenTime} ~ ${hotel.sunCloseTime}"
        title_week_time.append(weekTime)
        title_sat_time.append(satTime)
        title_sun_time.append(sunTime)

        when {
            hotel.monitorAvailable -> {
                tv_monitoring_contents.text = "제공"
            }
            else -> {
                tv_monitoring_contents.text = "미제공"
            }
        }

        when{
            hotel.isNeuteredOnly -> {
                tv_neuter_needed.text = "중성화 필요"
            }else -> {
            tv_neuter_needed.text = "중성화 필요 없음"
        }
        }

//        tv_week_price.append("${hotel.weekPrice} 원")
//        tv_sat_price.append("${hotel.satPrice} 원")
//        tv_sun_price.append("${hotel.sunPrice} 원")
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

        action_reserve_now.setOnClickListener { floatingActionsMenu.collapse() }

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

    private fun startOpenWebsiteIntent(hotelWebsite: String) {
        val openWebsiteIntent = Intent(Intent.ACTION_VIEW)
        openWebsiteIntent.data = Uri.parse(hotelWebsite)
        startActivity(openWebsiteIntent)
    }

    private fun startPhoneIntent(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse(phoneNumber)

        if(!isCallPermissionGranted()){
            requestCallPermission()
        }else{
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




object HotelViewHolderFactory{

    fun create(view : View, viewType : Int) : RecyclerView.ViewHolder{
        return when(viewType){
            R.layout.place_detail_review_view_holder -> ReviewViewHolder(view)
            R.layout.no_review_layout -> NoResultViewHolder(view)
            else -> {
                ReviewViewHolder(view)
            }
        }
    }

    class ReviewViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), GenericRecyclerViewAdapter.Binder<HotelReview>{

        override fun bind(data: HotelReview) {
            itemView.tv_user_name.text = data.userName
            itemView.tv_user_review.text = data.reviewContent
            itemView.user_rating.rating = data.rating.toFloat()
        }
    }

    class NoResultViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), GenericRecyclerViewAdapter.Binder<Any>{

        override fun bind(data: Any) {
            // 데이터 없고 그냥 리뷰 없음 레이아웃만 띄워주기
        }

    }

    private const val TAG = "PlaceInfoDetailsFragment"
}