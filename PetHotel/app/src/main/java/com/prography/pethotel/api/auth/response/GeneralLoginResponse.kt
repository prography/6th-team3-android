package com.prography.pethotel.api.auth.response

data class GeneralLoginResponse(
    val `data`: GeneralLoginData,
    val message: String,
    val status: String
)

data class GeneralLoginData(
    val token: String
)

//{
//  "status": "success",
//  "message": "Success General Login",
//  "data": {
//    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiZW1haWwiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTU5NDE4MDM1NywiZXhwIjoxNTk1NDc2MzU3fQ.HwvgXP7mJ01Np4hx0RNT73YM5ZmRwotCIRb4P2sALn0"
//  }
//}