package com.prography.pethotel.ui.reservation

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.prography.pethotel.R
import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.models.ReservationInfo
import com.prography.pethotel.room.auth.AccountPropertiesViewModel
import com.prography.pethotel.utils.AuthTokenViewModel
import kotlinx.android.synthetic.main.fragment_reservation_main_v2.*


private const val TAG = "ReservationFragment"
class ReservationFragment : Fragment() {

    /* Activity 가 갖고 있는 뷰 모델을 Fragment 에서 사용한다 */
    private val reservationViewModel: ReservationViewModel by activityViewModels()

    val authTokenViewModel : AuthTokenViewModel by activityViewModels()
    val accountPropertiesViewModel : AccountPropertiesViewModel by activityViewModels()

    private var checkInDateTime : String? = ""
    private var checkOutDateTime : String? = ""

    var inDate : String ?= ""
    var outDate : String ?= ""
    var inTime : String ?= ""
    var outTime : String ?= ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_reservation_main_v2, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /* 호텔 이름을 상단에 띄운다 */
        val hotel : HotelData = arguments?.get("hotel") as HotelData
        val hotelName = hotel.name

        /* 디스플레이 되는 날짜를 현재 날짜로 초기화 한다. */
        initDateTimeText()

        if(hotelName.isNotEmpty()){
            reservation_hotel_name.text = getString(R.string.reservation_hotel_name, hotelName)
        }else {
            Log.d(TAG, "onActivityCreated: 예약화면으로 호텔 정보 불러오기 실패")
        }

        /* 호텔 정보를 예약 뷰모델에 저장 */


        /* 날짜와 시간을 선택한 후 다음 화면으로 넘어간다. */
        proceed_reservation_from_main.setOnClickListener {
            if(isDateTimeValid(inDate, inTime, outDate, outTime)){
                checkInDateTime = "$inDate $inTime"
                checkOutDateTime = "$outDate $outTime"

                reservationViewModel.updateReservationInfo(
                    reservationInfo =
                ReservationInfo(
                    hotel = hotel,
                    checkInDateTime = checkInDateTime,
                    checkOutDateTime = checkOutDateTime
                ))
//                setDateTimeToViewModel(inDate, inTime, outDate, outTime)
                findNavController().navigate(R.id.action_reservationFragment2_to_reservationDetailFragment2)
            }else {
                Toast.makeText(requireContext(), "날짜와 시간을 정확히 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        /* 만약 취소하기를 누르면 이전 화면으로 돌아가고, reservation 정보는 초기화된다. */
        cancel_reservation_from_main.setOnClickListener {
            findNavController().navigateUp()
        }

        /*달력에서 날짜를 가져온다 */
        reservation_calendar.setOnRangeSelectedListener { widget, dates ->
            Log.d(TAG, "onActivityCreated: $widget, $dates")
            val checkInDate = dates[0]
            val ciYear = checkInDate.year
            val ciMonth = checkInDate.month
            val ciDay = checkInDate.day

            val checkOutDate = dates[dates.lastIndex]
            val coYear = checkOutDate.year
            val coMonth = checkOutDate.month
            val coDay = checkOutDate.day
            Log.d(TAG, "onActivityCreated: $checkInDate, $checkOutDate")

            checkin_date.text = "${ciYear}/${ciMonth}/${ciDay}"
            checkout_date.text = "${coYear}/${coMonth}/${coDay}"

            inDate = "${ciYear}/${ciMonth}/${ciDay}"
            outDate = "${coYear}/${coMonth}/${coDay}"
        }

        /* 체크인 시간을 다일로그에서 선택한다. */
        checkin_time.setOnClickListener {
            val cldr: Calendar = Calendar.getInstance()
            val hour: Int = cldr.get(Calendar.HOUR_OF_DAY)
            val minutes: Int = cldr.get(Calendar.MINUTE)
            val picker = TimePickerDialog(
                requireContext(),
                OnTimeSetListener { view, hourOfDay, minute ->
                    Log.d(TAG, "onActivityCreated: $view, $hourOfDay, $minute")
                    val minString : String = if(minute == 0){
                        minute.toString() + "0"
                    }else{
                        minute.toString()
                    }
                    checkin_time.text = "${hourOfDay}:${minString}"
                    inTime = "${hourOfDay}:${minString}"
                },
                hour, minutes, true
            )
            picker.show()
        }

        /* 체크아웃 시간을 다일로그에서 선택한다 */
        checkout_time.setOnClickListener {
            val cldr: Calendar = Calendar.getInstance()
            val hour: Int = cldr.get(Calendar.HOUR_OF_DAY)
            val minutes: Int = cldr.get(Calendar.MINUTE)
            val picker = TimePickerDialog(
                requireContext(),
                OnTimeSetListener { view, hourOfDay, minute ->
                    Log.d(TAG, "onActivityCreated: $view, $hourOfDay, $minute")
                    val minString : String = if(minute == 0){
                        minute.toString() + "0"
                    }else{
                        minute.toString()
                    }
                    checkout_time.text = "${hourOfDay}:${minString}"
                    outTime = "${hourOfDay}:${minString}"
                },
                hour, minutes, true
            )
            picker.show()
        }

    }

    private fun initDateTimeText() {
        val cal = Calendar.getInstance()
        checkin_date.text = "${cal.get(Calendar.YEAR)}/${cal.get(Calendar.MONTH) + 1}/${cal.get(Calendar.DAY_OF_MONTH)}"
        checkout_date.text = "${cal.get(Calendar.YEAR)}/${cal.get(Calendar.MONTH) + 1}/${cal.get(Calendar.DAY_OF_MONTH)}"

        checkin_time.text = "${cal.get(Calendar.HOUR_OF_DAY)}:${cal.get(Calendar.MINUTE)}"
        checkout_time.text = "${cal.get(Calendar.HOUR_OF_DAY)}:${cal.get(Calendar.MINUTE)}"
    }

    private fun isDateTimeValid(inDate: String?, inTime: String?,
                                outDate: String?, outTime: String?): Boolean {
        //TODO 호텔의 영업시간과 맞는지 확인할 것
        return !inDate.isNullOrEmpty() ||
                !inTime.isNullOrEmpty() ||
                !outDate.isNullOrEmpty() ||
                !outTime.isNullOrEmpty()
    }

//    private fun setDateTimeToViewModel(
//        inDate: String?, inTime: String?,
//        outDate: String?, outTime: String?
//    ) {
//        checkInDateTime = "$inDate $inTime"
//        checkOutDateTime = "$outDate $outTime"
//
//        reservationViewModel.setReservationDateTime(
//            checkInDateTime!!, checkOutDateTime!!
//        )
//    }

}
