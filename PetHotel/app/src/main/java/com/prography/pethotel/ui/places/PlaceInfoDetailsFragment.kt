package com.prography.pethotel.ui.places

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.getbase.floatingactionbutton.FloatingActionsMenu.OnFloatingActionsMenuUpdateListener
import com.prography.pethotel.R
import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.models.HotelReview
import com.prography.pethotel.ui.places.util.GenericRecyclerViewAdapter
import com.prography.pethotel.ui.reservation.ReservationActivity
import com.prography.pethotel.utils.*
import kotlinx.android.synthetic.main.floating_menu.*
import kotlinx.android.synthetic.main.hotel_price_layout.*
import kotlinx.android.synthetic.main.place_info_detail_descriptions_layout.*
import kotlinx.android.synthetic.main.place_info_detail_time_etc_layout.*
import kotlinx.android.synthetic.main.place_detail_review_view_holder.view.*


private const val TAG = "PlaceInfoDetailsFragmen"

class PlaceInfoDetailsFragment : Fragment() {


    private var phoneNumber : String? = ""
    private var hotelWebsite : String? = ""


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

        val weekTime = "${handleNullTimeStrings(hotel.weekOpenTime)} ~ ${handleNullTimeStrings(hotel.weekCloseTime)}"
        val satTime = "${handleNullTimeStrings(hotel.satOpenTime)} ~ ${handleNullTimeStrings(hotel.satCloseTime)}"
        val sunTime = "${handleNullTimeStrings(hotel.sunOpenTime)} ~ ${handleNullTimeStrings(hotel.sunCloseTime)}"

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

        /*호텔 가격 정보가 비어있지 않았다면 */

        var smallCriteria : String? = ""
        var mediumCriteria : String? = ""
        var largeCriteria : String? = ""

        if(!hotel.prices.isNullOrEmpty()){
            hotel.prices.forEach {
                p ->
                    val day = p.day
                    if(day == ALL_DAY){
                        when(p.size){
                            SMALL -> {
                                if(smallCriteria.isNullOrEmpty()){
                                    smallCriteria = convertGramToKilogram(p.weight)
                                }
                                sat_small_price.text = p.price.toString()
                                sun_small_price.text = p.price.toString()
                                week_small_price.text = p.price.toString()
                            }
                            MEDIUM -> {
                                if(mediumCriteria.isNullOrEmpty()){
                                    mediumCriteria = convertGramToKilogram(p.weight)
                                }
                                sat_medium_price.text = p.price.toString()
                                sun_medium_price.text = p.price.toString()
                                week_medium_price.text = p.price.toString()
                            }
                            LARGE -> {
                                if(largeCriteria.isNullOrEmpty()){
                                    largeCriteria = convertGramToKilogram(p.weight)
                                }
                                sat_large_price.text = p.price.toString()
                                sun_large_price.text = p.price.toString()
                                week_large_price.text = p.price.toString()
                            }
                         }
                    }else{
                        when (p.day) {
                            WEEK_DAY -> {
                                when(p.size){
                                    /* 첫번째 검사에서 사이즈 기준 확인하기 */
                                    SMALL -> {
                                        if(smallCriteria.isNullOrEmpty()){
                                            smallCriteria = convertGramToKilogram(p.weight)
                                        }
                                        week_small_price.text = p.price.toString()
                                    }
                                    MEDIUM -> {
                                        if(mediumCriteria.isNullOrEmpty()){
                                            mediumCriteria = convertGramToKilogram(p.weight)
                                        }
                                        week_medium_price.text = p.price.toString()
                                    }
                                    LARGE -> {
                                        if(largeCriteria.isNullOrEmpty()){
                                            largeCriteria = convertGramToKilogram(p.weight)
                                        }
                                        week_large_price.text = p.price.toString()
                                    }
                                }
                            }
                            SAT -> {
                                when(p.size){
                                    SMALL -> sat_small_price.text = p.price.toString()
                                    MEDIUM -> sat_medium_price.text = p.price.toString()
                                    LARGE -> sat_large_price.text = p.price.toString()
                                }
                            }
                            SUN -> {
                                when(p.size){
                                    SMALL -> sun_small_price.text = p.price.toString()
                                    MEDIUM -> sun_medium_price.text = p.price.toString()
                                    LARGE -> sun_large_price.text = p.price.toString()
                                }
                            }
                            else -> {
                                Log.d(TAG, "setHotelDataToView: 호텔 데이터 에러")
                            }
                        }
                    }
                }
            }

        size_criteria.text = getString(R.string.size_criteria_string, smallCriteria, mediumCriteria, largeCriteria)
    }

    private fun handleNullTimeStrings(time : String?) : String{
        return if(time.isNullOrEmpty()){
            "변동 가능"
        }else{
            time
        }
    }

    private fun convertGramToKilogram(weight : Int) : String{
        if(weight == 0){
            return "없음"
        }
        return (weight / 1000.00f).toString()
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

        /* 해당 호텔 정보를 갖고 예약 화면으로 넘어간다 */
        action_reserve_now.setOnClickListener {
            val intent = Intent(requireActivity(), ReservationActivity::class.java)
            requireActivity().startActivity(intent)
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