package com.prography.pethotel.api.auth.request

data class KakaoRegisterBody(
    val `data`: KakaoRegisterData,
    val userId: Int
)

data class KakaoRegisterData(
    val nickname: String,
    val phoneNumber: String,
    val profileImage: String
)