package com.prography.pethotel.api.auth.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KakaoRegistrationResponse(
    val status : String,
    val message : String,
    val data : KakaoRegistrationData
) : Parcelable

@Parcelize
data class KakaoRegistrationData(
    val userId : Int,
    val information : Information
) : Parcelable

@Parcelize
data class Information(
    val nickname : String,
    val profileImage : String,
    val email : String
) : Parcelable