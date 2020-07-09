package com.prography.pethotel.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PetInfo(
    var name : String? = "",
    var registrationNum : String? = "",
    var birthYear : String? = "",
    var sexNm : String? = "", //성별
    var kindNm : String? = "", //품종
    var neuterYn : String? = "", //중성화 여부
    var orgNm : String? = "", //관할 기관
    var officeTel : String? = "", //관할기관 연락처
    var aprGbNm : String? = "" //승인 여부
) : Parcelable