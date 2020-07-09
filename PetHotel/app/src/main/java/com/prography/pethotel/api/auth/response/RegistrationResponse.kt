package com.prography.pethotel.api.auth.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


//일반 회원가입시 가입 후 응답으로 오는 모델
@Parcelize
data class RegistrationResponse(
    @SerializedName("data")
    val userToken: UserToken,
    val message: String,
    val status: String
):Parcelable

@Parcelize
data class UserToken(
    val token: String
): Parcelable


//{
//  "status": "success",
//  "message": "Success New User Creation",
//  "data": {
//    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MywiZW1haWwiOiJzYXJhaGFuNzc0QGdtYWlsLmNvbSIsImlhdCI6MTU5NDExNzc5OSwiZXhwIjoxNTk1NDEzNzk5fQ.LOum03-UDlibc9wakoxo990-2U2MULqkSnzKR0xlCA4"
//  }
//}


//POST https://api.mypetmily.net/user
//Content-Type: application/json
//
//{
//	"data": {
//		"name": "sara",
//		"phoneNumber": "01071528590",
//		"email": "sarahan774@gmail.com",
//		"password": "samplepwpwpw"
//	}
//}