package com.prography.pethotel.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class HotelReview(
    val reviewId : Int,
    val createdAt : String,
    val updatedAt : String,
    val userName : String,
    val hotelName : String,
    val reviewContent : String,
    val rating : Int
) : Parcelable


@Parcelize
data class EmptyReview(
    val noting : Int
) : Parcelable