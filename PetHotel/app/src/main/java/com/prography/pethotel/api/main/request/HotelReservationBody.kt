package com.prography.pethotel.api.main.request

data class HotelReservationBody(
    val userId : Int,
    val data : HotelReservationData
)

data class HotelReservationData(
    val perPetRequests : ArrayList<PerPetRequests>
)

data class PerPetRequests(
    val petId : Int,
    val request : String
)