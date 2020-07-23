package com.prography.pethotel.models

import okhttp3.MultipartBody

data class GeneralUserInfo(
    var nickName : String? = null,
    var email : String? = null,
    var password : String? = null,
    var phoneNumber : String? = null,
    var userImagePart : MultipartBody.Part ? = null,
    var userImageUrl : String ?= null,
    var pets : ArrayList<PetInfo>? = null
)