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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.getbase.floatingactionbutton.FloatingActionsMenu.OnFloatingActionsMenuUpdateListener
import com.prography.pethotel.R
import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.api.main.response.HotelPrice
import com.prography.pethotel.ui.places.adapters.PriceAdapter
import kotlinx.android.synthetic.main.floating_menu.*
import kotlinx.android.synthetic.main.fragment_place_info_details_v2.*

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

        val hotel : HotelData = arguments?.get("hotel") as HotelData
        phoneNumber = hotel.phoneNumber
        hotelWebsite = hotel.pageLink

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

        /*리뷰 관련 기능은 일시적으로 커맨트 처리 */
//        val list = hotel.reviews ?: arrayListOf(EmptyReview(0))
//        val list = hotel.review ?: DummyData.hotelReviews
//
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
//
//        rv_hotel_review.apply {
//            adapter = myAdapter
//            setHasFixedSize(true)
//            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        }
    }


    @SuppressLint("SetTextI18n")
    private fun setHotelDataToView(hotel : HotelData){
        place_detail_name.text = hotel.name
        place_detail_address.text = hotel.addressDetail
        tv_place_detail.text = hotel.description

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




//object HotelViewHolderFactory{
//
//    fun create(view : View, viewType : Int) : RecyclerView.ViewHolder{
//        return when(viewType){
//            R.layout.place_detail_review_view_holder -> ReviewViewHolder(view)
//            R.layout.no_review_layout -> NoResultViewHolder(view)
//            else -> {
//                ReviewViewHolder(view)
//            }
//        }
//    }
//
//    class ReviewViewHolder(itemView: View)
//        : RecyclerView.ViewHolder(itemView), GenericRecyclerViewAdapter.Binder<HotelReview>{
//
//        override fun bind(data: HotelReview) {
//            itemView.tv_user_name.text = data.userName
//            itemView.tv_user_review.text = data.reviewContent
//            itemView.user_rating.rating = data.rating.toFloat()
//        }
//    }
//
//    class NoResultViewHolder(itemView: View)
//        : RecyclerView.ViewHolder(itemView), GenericRecyclerViewAdapter.Binder<Any>{
//
//        override fun bind(data: Any) {
//            // 데이터 없고 그냥 리뷰 없음 레이아웃만 띄워주기
//        }
//
//    }
//
//    private const val TAG = "PlaceInfoDetailsFragment"
//}
