package com.prography.pethotel.api.main.request

data class UpdateReservationBody(
    val userId : Int,
    val data : UpdateReservationData
)

data class UpdateReservationData(
    val petId : Int,
    val request : String
)
