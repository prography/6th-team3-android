package com.prography.pethotel.api.auth.request

//로그인시 서버로 POST하는 Body 모델
data class LoginInfoBody(
    val email : String,
    val password : String
)