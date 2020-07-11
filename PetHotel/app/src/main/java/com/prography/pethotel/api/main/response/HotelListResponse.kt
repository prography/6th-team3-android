package com.prography.pethotel.api.main.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


//호텔 리스트 요청 후 오는 응답에 대한 모델

@Parcelize
data class HotelListResponse(
    val `data`: List<HotelData>,
    val message: String,
    val status: Int
) : Parcelable

@Parcelize
data class HotelData(
    val address: String,
    val addressDetail: String,
    val createdAt: String,
    val description: String,
    val id: Int,
    val isNeuteredOnly: Boolean,
    val largeCriteria: Int,
    val latitude: Double,
    val longitude: Double,
    val maxDogSize: Int,
    val mediumCriteria: Int,
    val monitorAvailable: Boolean,
    val name: String,
    val pageLink: String,
    val phoneNumber: String,
    val prices: List<HotelPrice>, //weekday sunday saturday all
    val satCloseTime: String?,
    val satOpenTime: String?,
    val sunCloseTime: String?,
    val sunOpenTime: String?,
    val updatedAt: String,
    val weekCloseTime: String?,
    val weekOpenTime: String?,
    val zipcode: String
) : Parcelable{
    var hotelImageLinks : List<HotelImage> = emptyList()
}
