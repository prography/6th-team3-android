package com.prography.pethotel.api.auth.request

data class RegisterPetBody(
    val `data`: List<PetData>,
    val userId: Int
)

data class PetData(
    val birthYear: Int,
    val petName: String,
    val registerNumber: String
)