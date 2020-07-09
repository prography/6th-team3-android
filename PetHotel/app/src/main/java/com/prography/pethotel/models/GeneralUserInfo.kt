package com.prography.pethotel.models

data class GeneralUserInfo(
    var nickName : String? = null,
    var email : String? = null,
    var password : String? = null,
    var phoneNumber : String? = null,
    var userImagePath : String ? = null,
    var pets : ArrayList<PetInfo>? = null
)