package com.prography.pethotel.api.main.response

import android.os.Parcelable
import com.prography.pethotel.room.entities.Price
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
    val latitude: Double?= null,
    val longitude: Double?= null,
    val maxDogSize: Int,
    val mediumCriteria: Int,
    val monitorAvailable: Boolean,
    val name: String,
    val pageLink: String ?= "",
    val phoneNumber: String ?= "",
    var prices: List<HotelPrice>,
    val satCloseTime: String?="변동가능",
    val satOpenTime: String?="변동가능",
    val sunCloseTime: String?="변동가능",
    val sunOpenTime: String?="변동가능",
    val updatedAt: String,
    val weekCloseTime: String? = "변동가능",
    val weekOpenTime: String? = "변동가능",
    val zipcode: String
) : Parcelable{
    var hotelImageLinks : List<HotelImage> = emptyList()
    var distanceFromUser : Int = Int.MAX_VALUE
}
