package com.prography.pethotel.api.auth.request


// 일반 회원가입시 POST할 때 Body 로 넣는 모델
data class RegisterUserInfo(
    val `data`: UserData,
    val profileImage : String ?= ""
)

data class UserData(
    val nickname: String,
    val phoneNumber: String? = "",
    val email: String,
    val password: String
)