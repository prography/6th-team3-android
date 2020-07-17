package com.prography.pethotel.ui.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.models.DeliverMsg
import com.prography.pethotel.models.GeneralUserInfo
import com.prography.pethotel.models.PetInfo
import com.prography.pethotel.models.ReservationInfo

class ReservationViewModel : ViewModel(){

    val _reservationInfo : MutableLiveData<ReservationInfo> = MutableLiveData()
    val reservationInfo : LiveData<ReservationInfo> get() = _reservationInfo


    //actions
    fun setReservationDateTime(checkInDateTime : String, checkOutDateTime : String){
        _reservationInfo.value?.checkInDateTime = checkInDateTime
        _reservationInfo.value?.checkOutDateTime = checkOutDateTime
    }

    fun setReservationPetInfo(petList : ArrayList<PetInfo>){
        _reservationInfo.value?.petList = petList
    }

    fun setReservationHotelInfo(hotelInfo : HotelData){
        _reservationInfo.value?.hotel = hotelInfo
    }

    fun setReservationUserInfo(userInfo : GeneralUserInfo){
        _reservationInfo.value?.userInfo = userInfo
    }

    fun setDeliverMessage(msgList : ArrayList<DeliverMsg>){
        _reservationInfo.value?.deliverMsg = msgList
    }
}