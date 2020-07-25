package com.prography.pethotel.ui.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.models.ReservationInfo

class ReservationViewModel : ViewModel() {

    val _reservationInfo: MutableLiveData<ReservationInfo> = MutableLiveData()
    val reservationInfo: LiveData<ReservationInfo>
        get() = _reservationInfo

    fun updateReservationInfo(reservationInfo : ReservationInfo){
        _reservationInfo.value = reservationInfo
    }

    //actions
    fun setReservationDateTime(checkInDateTime: String, checkOutDateTime: String) {
        _reservationInfo.value?.checkInDateTime = checkInDateTime
        _reservationInfo.value?.checkOutDateTime = checkOutDateTime
    }

    fun setReservationHotelInfo(hotelInfo: HotelData) {
        _reservationInfo.value?.hotel = hotelInfo

    }
}