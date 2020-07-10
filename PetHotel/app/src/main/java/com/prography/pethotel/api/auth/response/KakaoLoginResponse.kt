package com.prography.pethotel.api.auth.response

data class KakaoLoginResponse(
    val token : String,
    val message: String,
    val status: String
)

//카카오로 가입하기 => 카카오버튼 클릭 => auth/~ 로 요청 보내서 토큰 받기
//토큰 받은걸로 유저 아이디 받기,