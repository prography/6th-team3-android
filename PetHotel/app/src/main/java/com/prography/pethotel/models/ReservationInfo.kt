package com.prography.pethotel.models

import com.prography.pethotel.api.main.response.HotelData

data class ReservationInfo(
    var hotel : HotelData,
    var userInfo : GeneralUserInfo,
    var petList : ArrayList<PetInfo>,
    var deliverMsg : ArrayList<DeliverMsg> ?= ArrayList(),
    var checkInDateTime : String,
    var checkOutDateTime : String
)

data class DeliverMsg(
    val petName : String,
    val petKind : String? = "",
    val msg : String? = ""
)