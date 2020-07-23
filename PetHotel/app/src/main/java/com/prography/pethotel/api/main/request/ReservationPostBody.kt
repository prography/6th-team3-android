package com.prography.pethotel.api.main.request

data class ReservationPostBody (
    val userId : Int,
    val data : List<ReservationPostData>
)

data class ReservationPostData(
    val petId : Int,
    val request : String
)
