package com.prography.pethotel.api.main.response

//토큰으로 유저 정보 요청하면 오는 응답에 대한 모델
data class UserInfoResponse(
    val id: Int,
    val name: String,
    val phoneNumber: String,
    val profileImage : String
)