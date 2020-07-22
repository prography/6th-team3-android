package com.prography.pethotel.api.main.request


data class PostPetBody(
    val userId : Int,
    val data : PetPostData
)

data class PetPostData(
    val petName : String,
    val photoUrl : String,
    val registerNumber : String,
    val birthYear : Int
)