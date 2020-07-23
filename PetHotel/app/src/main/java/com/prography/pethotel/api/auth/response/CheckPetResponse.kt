package com.prography.pethotel.api.auth.response

data class CheckPetResponse(
    val `data`: CheckPetResponseData,
    val message: String,
    val status: String
)

data class CheckPetResponseData(
    val breed: String,
    val gender: String,
    val isNeutered: Boolean,
    val registerNumber: String,
    val rfidCode: String
)