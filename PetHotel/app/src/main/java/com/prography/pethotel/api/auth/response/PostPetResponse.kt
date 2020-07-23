package com.prography.pethotel.api.auth.response

data class PostPetResponse(
    val `data`: PostPetResponseData,
    val message: String,
    val status: String
)

data class PostPetResponseData(
    val breed: String,
    val createdAt: String,
    val gender: String,
    val id: Int,
    val isNeutered: Boolean,
    val name: String,
    val registerNum: String,
    val rfidCode: String,
    val updatedAt: String,
    val userId: Int,
    val weight: Any,
    val year: Int
)