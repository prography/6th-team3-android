package com.prography.pethotel.api.auth.request

import com.google.gson.annotations.SerializedName

data class RegisterPetBody(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("data")
    val `data`: List<PetData>
)

data class PetData(
    @SerializedName("petName")
    val petName: String,
    @SerializedName("registerNumber")
    val registerNumber: String,
    @SerializedName("birthYear")
    val birthYear: Int
)