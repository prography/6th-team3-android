package com.prography.pethotel.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Hotel(
    val hotelId : Int,
    val createdAt : String,
    val updatedAt : String,
    val hotelName : String,
    val description : String,
    val address: String,
    val addressDetail: String,
    val zipcode: String,
    val latitude: String,
    val longitude: String,
    val weekOpenTime: String,
    val weekCloseTime: String,
    val satOpenTime: String,
    val satCloseTime: String,
    val sunOpenTime: String,
    val sunCloseTime: String,
    val weekPrice: Int,
    val satPrice: Int,
    val sunPrice: Int,
    val phoneNumber : String,
    val monitorAvailable : Boolean,
    val isNeuteredOnly : Boolean,
    val maxDogSize : Int,
    val pageLink : String,
    var reviews : ArrayList<HotelReview>? = null
) : Parcelable
// TODO 호텔 리뷰 서버에서 받은 것 호텔 정보들과 합쳐서 데이터 클래스 만들기. Transformations.map