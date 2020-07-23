package com.prography.pethotel.api.auth.request

data class CheckPetBody(
    val petName : String,
    val registerNumber : String,
    val birthYear : Int
)