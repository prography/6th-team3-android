package com.prography.pethotel.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PetInfo(
    var name : String? = "",
    var registrationNum : String? = "",
    var birthYear : String? = "",
    val sexNm : String? = "", //성별
    val kindNm : String? = "", //품종
    val neuterYn : String? = "", //중성화 여부
    val orgNm : String? = "", //관할 기관
    val officeTel : String? = "", //관할기관 연락처
    val aprGbNm : String? = "" //승인 여부
) : Parcelable