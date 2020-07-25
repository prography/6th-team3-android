package com.prography.pethotel.models

import com.prography.pethotel.api.main.response.HotelData
import com.prography.pethotel.room.entities.Pet

data class ReservationInfo(
    var hotel : HotelData ?= null,
    var deliverMsg : String ?= null,
    var checkInDateTime : String ?= null,
    var checkOutDateTime : String ?= null
)